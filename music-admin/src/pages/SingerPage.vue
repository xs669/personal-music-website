<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <el-button
          type="danger"
          size="mini"
          @click="handleDelAll"
          :disabled="control"
          >批量删除</el-button
        >
        <el-input
          v-model="select_word"
          size="mini"
          placeholder="请输入歌手名"
          class="handle-input"
          @keyup.enter.native="search"
        ></el-input>
        <el-button
          type="primary"
          size="mini"
          @click="centerDialogVisible = true"
          >添加歌手</el-button
        >
      </div>
    </div>
    <el-table
      size="mini"
      border
      style="width: 100%"
      height="500px"
      :data="tableData"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40px"></el-table-column>
      <el-table-column label="歌手图片" width="110px" align="center">
        <template v-slot="scope">
          <div class="singer-img">
            <img :src="scope.row.pic" style="width: 100%" alt="头像" />
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="歌手"
        width="120px"
        align="center"
      ></el-table-column>
      <el-table-column label="性别" align="center" width="50px" prop="sex">
        <template v-slot="scope">
          {{ getSex(scope.row.sex) }}
        </template>
      </el-table-column>
      <el-table-column label="生日" width="120px" align="center">
        <template v-slot="scope">
          {{ String(scope.row.birth).substring(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column
        label="地区"
        align="center"
        prop="location"
        width="100px"
      ></el-table-column>
      <el-table-column label="简介" align="center">
        <template v-slot="scope">
          <p style="height: 100px; overflow: scroll">
            {{ scope.row.introduction }}
          </p>
        </template>
      </el-table-column>
      <el-table-column label="歌曲管理" width="110px" align="center">
        <template v-slot="scope">
          <el-button
            type="primary"
            size="mini"
            @click="songEdit(scope.row.id, scope.row.name)"
            >歌曲管理</el-button
          >
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150px" align="center">
        <template v-slot="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.row)"
            >编辑</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        background
        layout="total,prev,pager,next"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="singerCount"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="添加歌手"
      :visible.sync="centerDialogVisible"
      width="400px"
      center
    >
      <el-form
        :model="registerForm"
        :rules="rules"
        ref="registerForm"
        label-width="80px"
      >
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-input v-model="registerForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="pic" label="头像" size="mini">
          <el-upload
            class="avatar-uploader"
            action="/api/upload"
            name="pic"
            :multiple="true"
            :auto-upload="true"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img
              v-if="registerForm.pic"
              :src="registerForm.pic"
              class="avatar"
              alt="头像"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker
            type="date"
            v-model="registerForm.birth"
            placeholder="选择日期"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-select
            v-model="registerForm.location"
            filterable
            placeholder="请选择国籍"
          >
            <el-option
              v-for="item in country"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input
            type="textarea"
            v-model="registerForm.introduction"
            placeholder="简介"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button
          size="mini"
          type="primary"
          @click="centerDialogVisible = false"
          >取消</el-button
        >
        <el-button size="mini" type="primary" @click="addSinger"
          >确定</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="修改歌手"
      :visible.sync="editVisible"
      width="400px"
      center
    >
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-input v-model="form.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="form.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
            <el-radio :label="3">不明</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="pic" label="头像" size="mini">
          <el-upload
            class="avatar-uploader"
            action="/api/upload"
            name="pic"
            :multiple="true"
            :auto-upload="true"
            :show-file-list="false"
            :on-success="updateAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="form.pic" :src="form.pic" class="avatar" alt="头像" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker
            type="date"
            v-model="form.birth"
            placeholder="选择日期"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-select
            v-model="form.location"
            filterable
            placeholder="请选择国籍"
          >
            <el-option
              v-for="item in country"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input
            type="textarea"
            v-model="form.introduction"
            placeholder="简介"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" type="primary" @click="editVisible = false"
          >取消</el-button
        >
        <el-button size="mini" type="primary" @click="editSave">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="删除歌手" :visible.sync="delVisible" width="300px" center>
      <div style="text-align: center">删除不可恢复，是否确定删除</div>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="delVisible = false"
          >取消</el-button
        >
        <el-button type="primary" size="mini" @click="deleteRow"
          >确定</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="批量删除歌手"
      :visible.sync="delAllVisible"
      width="300px"
      center
    >
      <div style="text-align: center">批量删除不可恢复，是否确定删除</div>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="delAllVisible = false"
          >取消</el-button
        >
        <el-button type="primary" size="mini" @click="delAll">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  addSinger,
  getSingerByName,
  updateSinger,
  deleteSinger,
  deleteAllSinger,
  getSingerCount,
  getAllSingerByPage,
} from "@/api/Singer";
import country from "../assets/js/country";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Singer",
  data() {
    return {
      centerDialogVisible: false, // 新增弹窗
      editVisible: false, // 编辑弹窗
      delVisible: false, // 删除窗口
      delAllVisible: false, // 批量删除窗口
      idx: "", // 删除行id
      control: true, // 控制按钮禁用状态
      multipleSelection: [], // 勾选项
      registerForm: {
        name: "",
        sex: "",
        pic: "",
        birth: "",
        location: "",
        introduction: "",
      },
      form: {
        id: "",
        name: "",
        sex: "",
        pic: "",
        birth: "",
        location: "",
        introduction: "",
      },
      country: "",
      tableData: [],
      singerCount: 0,
      select_word: "",
      pageSize: 4,
      currentPage: 1,
      rules: {
        name: [{ required: true, message: "请输入歌手名", trigger: "blur" }],
        sex: [{ required: true, message: "请输入歌手性别", trigger: "blur" }],
        birth: [{ required: true, message: "请选择歌手生日", trigger: "blur" }],
        location: [
          { required: true, message: "请输入歌手所在地区", trigger: "blur" },
        ],
        introduction: [
          { required: true, message: "请输入歌手简介", trigger: "blur" },
        ],
        pic: [{ required: true, message: "请上传格式头像", trigger: "blur" }],
      },
    };
  },
  watch: {
    currentPage(val) {
      if (val) {
        this.getAllData();
      }
    },
    centerDialogVisible(val) {
      if (val === false) {
        this.registerForm = {};
      }
    },
    select_word(val) {
      if (val === "") {
        this.getAllData();
      }
    },
  },
  created() {
    this.getAllData();
    this.country = country;
  },
  methods: {
    getSex(sex) {
      if (sex === 1) {
        return "男";
      } else if (sex === 0) {
        return "女";
      } else if (sex === 2) {
        return "组合";
      } else if (sex === 3) {
        return "不明";
      }
    },
    // 跳转歌曲管理页面
    songEdit(id, name) {
      this.$router.push({ path: "/Song", query: { id, name } });
    },
    handleDelAll() {
      this.delAllVisible = true;
    },
    // 批量删除
    delAll() {
      let ids = this.multipleSelection;
      deleteAllSinger(ids).then((res) => {
        if (res.code === 200) {
          this.delAllVisible = false;
          this.$message.success(res.msg);
          this.multipleSelection = [];
          this.getAllData();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    // 选中数据
    handleSelectionChange(val) {
      if (val.length === 0) {
        this.multipleSelection = [];
        this.control = true;
      } else {
        for (let index in val) {
          this.multipleSelection.push(val[index].id);
          this.control = false;
        }
      }
    },
    handleDelete(id) {
      this.idx = id;
      this.delVisible = true;
    },
    // 删除功能
    deleteRow() {
      let id = this.idx;
      deleteSinger(id).then((res) => {
        if (res.code === 200) {
          this.delVisible = false;
          this.$message.success(res.msg);
          this.getAllData();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    // 编辑功能
    editSave() {
      let Singer = this.form;
      updateSinger(Singer).then((res) => {
        if (res.code === 200) {
          this.editVisible = false;
          this.$message.success(res.msg);
          this.getAllData();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    handleEdit(row) {
      this.editVisible = true;
      this.form = row;
    },
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    search() {
      let name = this.select_word;
      getSingerByName(name).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        } else {
          this.tableData = [];
        }
      });
    },
    getAllData() {
      this.tableData = [];
      getAllSingerByPage(this.currentPage).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        }
      });
      getSingerCount().then((res) => {
        if (res.code === 200) {
          this.singerCount = res.data;
        }
      });
    },
    // 添加歌手
    addSinger() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          let Sing = this.registerForm;
          addSinger(Sing).then((res) => {
            if (res.code === 200) {
              this.$message.success(res.msg);
              this.getAllData();
              this.registerForm = [];
            } else {
              this.$message.error(res.msg);
            }
          });
          this.centerDialogVisible = false;
        }
      });
    },
    updateAvatarSuccess(res) {
      if (res.code === 20000) {
        this.form.pic = res.data;
        this.$message.success(res.message);
      } else {
        this.$message.error(res.message);
      }
    },
    handleAvatarSuccess(res) {
      if (res.code === 20000) {
        this.registerForm.pic = res.data;
        this.$message.success(res.message);
      } else {
        this.$message.error(res.message);
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      if (!isJPG) {
        this.$message.warning("上传头像图片只能是 JPG 格式!");
      }
      return isJPG;
    },
  },
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  margin-bottom: 20px;
  width: 178px;
  height: 178px;
  display: block;
}
.singer-img {
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
.handle-input {
  width: 300px;
  display: inline-block;
}
.pagination {
  display: flex;
  justify-content: center;
}
</style>
