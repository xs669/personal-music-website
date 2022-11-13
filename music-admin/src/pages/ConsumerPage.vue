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
          placeholder="请输入用户名"
          class="handle-input"
          @keyup.enter.native="search"
        ></el-input>
        <el-button
          type="primary"
          size="mini"
          @click="centerDialogVisible = true"
          >添加新用户</el-button
        >
      </div>
    </div>
    <el-table
      size="mini"
      border
      style="width: 100%"
      height="500px"
      :data="data"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40px"></el-table-column>
      <el-table-column label="用户图片" width="110px" align="center">
        <template v-slot="scope">
          <div class="consumer-img">
            <img :src="scope.row.avatar" style="width: 100%" alt="头像" />
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        width="100px"
        align="center"
      ></el-table-column>
      <el-table-column label="性别" align="center" width="50px" prop="sex">
        <template v-slot="scope">
          {{ scope.row.sex === 1 ? "男" : "女" }}
        </template>
      </el-table-column>
      <el-table-column
        prop="phoneNum"
        label="手机号"
        width="120px"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="email"
        label="电子邮箱"
        width="140px"
        align="center"
      ></el-table-column>
      <el-table-column label="生日" width="120px" align="center">
        <template v-slot="scope">
          {{ String(scope.row.birth).substring(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="签名" align="center">
        <template v-slot="scope">
          {{ scope.row.introduction }}
        </template>
      </el-table-column>
      <el-table-column
        label="地区"
        align="center"
        prop="location"
        width="80px"
      ></el-table-column>
      <el-table-column label="管理" align="center" width="80px">
        <template v-slot="scope">
          <el-button
            style="margin-bottom: 10px"
            size="mini"
            type="primary"
            @click="goCollect(scope.row.id)"
            >收藏</el-button
          >
          <el-button
            style="margin: 0 auto"
            size="mini"
            type="primary"
            @click="goComment(scope.row.id)"
            >评论</el-button
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
        :total="tableData.length"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="添加用户"
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
        <el-form-item prop="username" label="用户名" size="mini">
          <el-input
            v-model="registerForm.username"
            placeholder="用户名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
          <el-input
            type="password"
            v-model="registerForm.password"
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="avatar" label="头像" size="mini">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8888/upload"
            name="pic"
            :multiple="true"
            :auto-upload="true"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img
              v-if="registerForm.avatar"
              :src="registerForm.avatar"
              class="avatar"
              alt="头像"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-input
            v-model="registerForm.phoneNum"
            placeholder="手机号"
          ></el-input>
        </el-form-item>
        <el-form-item prop="email" label="电子邮箱" size="mini">
          <el-input
            v-model="registerForm.email"
            placeholder="电子邮箱"
          ></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker
            type="date"
            v-model="registerForm.birth"
            placeholder="选择日期"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名" size="mini">
          <el-input
            type="textarea"
            v-model="registerForm.introduction"
            placeholder="签名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-select
            v-model="registerForm.location"
            filterable
            placeholder="请选择城市"
          >
            <el-option
              v-for="item in citys"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button
          size="mini"
          type="primary"
          @click="centerDialogVisible = false"
          >取消</el-button
        >
        <el-button size="mini" type="primary" @click="addSave">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="修改用户信息"
      :visible.sync="editVisible"
      width="400px"
      center
    >
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item prop="username" label="用户名" size="mini">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
          <el-input
            type="password"
            v-model="form.password"
            placeholder="密码"
          ></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="form.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="avatar" label="头像" size="mini">
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8888/upload"
            name="pic"
            :multiple="true"
            :auto-upload="true"
            :show-file-list="false"
            :on-success="updateAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img
              v-if="form.avatar"
              :src="form.avatar"
              class="avatar"
              alt="头像"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-input v-model="form.phoneNum" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="电子邮箱" size="mini">
          <el-input v-model="form.email" placeholder="电子邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker
            type="date"
            v-model="form.birth"
            placeholder="选择日期"
            style="width: 100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名" size="mini">
          <el-input
            type="textarea"
            v-model="form.introduction"
            placeholder="签名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-select
            v-model="form.location"
            filterable
            placeholder="请选择城市"
          >
            <el-option
              v-for="item in citys"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
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
  addConsumer,
  deleteAllConsumer,
  deleteConsumer,
  getAllConsumer,
  getConsumerByName,
  updateConsumer,
} from "@/api/Consumer";
import { isEmail, isPhoneNum } from "@/plugins/validate";
import cities from "../assets/js/city";

let validateEmail = (rule, value, callback) => {
  if (!isEmail(value)) {
    callback(new Error("邮箱格式错误"));
  } else {
    callback();
  }
};

let validatePhoneNum = (rule, value, callback) => {
  if (!isPhoneNum(value)) {
    callback(new Error("手机号格式错误"));
  } else {
    callback();
  }
};

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Consumer",
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
        username: "",
        password: "",
        sex: "",
        phoneNum: "",
        email: "",
        birth: "",
        introduction: "",
        location: "",
        avatar: "",
      },
      form: {
        id: "",
        username: "",
        password: "",
        sex: "",
        phoneNum: "",
        email: "",
        birth: "",
        introduction: "",
        location: "",
        avatar: "",
      },
      citys: [],
      tableData: [],
      select_word: "",
      pageSize: 4,
      currentPage: 1,
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }],
        phoneNum: [
          { required: true, message: "请输入电话号码", trigger: "blur" },
          { validator: validatePhoneNum, trigger: "blur" },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          { validator: validateEmail, trigger: "blur" },
        ],
        birth: [{ required: true, message: "请选择用户生日", trigger: "blur" }],
        introduction: [
          { required: true, message: "请输入用户签名", trigger: "blur" },
        ],
        location: [
          { required: true, message: "请输入用户所在地区", trigger: "blur" },
        ],
        avatar: [{ required: true, message: "请上传头像", trigger: "blur" }],
      },
    };
  },
  watch: {
    select_word(val) {
      if (val === "") {
        this.getAllData();
      }
    },
    centerDialogVisible(val) {
      if (val === false) {
        this.registerForm = {};
      }
    },
  },
  created() {
    this.getAllData();
    this.citys = cities;
  },
  computed: {
    data() {
      return this.tableData.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      );
    },
  },
  methods: {
    goCollect(id) {
      this.$router.push({ path: "/collect", query: { userId: id } });
    },
    goComment(id) {
      this.$router.push({ path: "/comment", query: { userId: id } });
    },
    handleDelAll() {
      this.delAllVisible = true;
    },
    // 批量删除
    delAll() {
      let ids = this.multipleSelection;
      deleteAllConsumer(ids).then((res) => {
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
      deleteConsumer(id).then((res) => {
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
      updateConsumer(Singer).then((res) => {
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
      getConsumerByName(name).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        } else {
          this.tableData = [];
        }
      });
    },
    getAllData() {
      this.tableData = [];
      getAllConsumer().then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        }
      });
    },
    // 添加功能
    addSave() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          let Sing = this.registerForm;
          addConsumer(Sing).then((res) => {
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
        this.form.avatar = res.data;
        this.$message.success(res.message);
      } else {
        this.$message.error(res.message);
      }
    },
    handleAvatarSuccess(res) {
      if (res.code === 20000) {
        this.registerForm.avatar = res.data;
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
.consumer-img {
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
