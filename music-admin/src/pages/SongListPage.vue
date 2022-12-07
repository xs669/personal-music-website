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
          placeholder="请输入标题"
          class="handle-input"
          @keyup.enter.native="search"
        ></el-input>
        <el-button
          type="primary"
          size="mini"
          @click="centerDialogVisible = true"
          >添加歌单</el-button
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
      <el-table-column label="歌曲图片" width="110px" align="center">
        <template v-slot="scope">
          <div class="song-img">
            <img :src="scope.row.pic" style="width: 100%" alt="歌单图片" />
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="title"
        label="标题"
        width="120px"
        align="center"
      ></el-table-column>
      <el-table-column prop="introduction" label="简介" align="center">
        <template v-slot="scope">
          <p style="height: 100px; overflow: scroll">
            {{ scope.row.introduction }}
          </p>
        </template>
      </el-table-column>
      <el-table-column
        prop="style"
        label="风格"
        align="center"
        width="120px"
      ></el-table-column>
      <el-table-column label="歌曲管理" width="110px" align="center">
        <template v-slot="scope">
          <el-button type="primary" size="mini" @click="songEdit(scope.row.id)"
            >歌曲管理</el-button
          >
        </template>
      </el-table-column>
      <el-table-column label="评论" width="80px" align="center">
        <template v-slot="scope">
          <el-button type="primary" size="mini" @click="goComment(scope.row.id)"
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
        :total="songListCount"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </div>
    <el-dialog
      title="添加歌单"
      :visible.sync="centerDialogVisible"
      width="400px"
      center
    >
      <el-form
        :model="registerForm"
        ref="registerForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item prop="title" label="标题" size="mini">
          <el-input v-model="registerForm.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item prop="pic" label="歌单图片" size="mini">
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
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input
            type="textarea"
            v-model="registerForm.introduction"
            placeholder="简介"
          ></el-input>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="registerForm.style" placeholder="风格"></el-input>
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
      title="修改歌单"
      :visible.sync="editVisible"
      width="400px"
      center
    >
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item prop="title" label="标题" size="mini">
          <el-input v-model="form.title" placeholder="标题"></el-input>
        </el-form-item>
        <el-form-item prop="pic" label="歌单图片" size="mini">
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
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input
            type="textarea"
            v-model="form.introduction"
            placeholder="简介"
          ></el-input>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="form.style" placeholder="风格"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" type="primary" @click="editVisible = false"
          >取消</el-button
        >
        <el-button size="mini" type="primary" @click="editSave">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="删除歌曲" :visible.sync="delVisible" width="300px" center>
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
      title="批量删除歌曲"
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
  addSongList,
  deleteAllSongList,
  deleteSongList,
  getAllSongListByPage,
  getSongListByName,
  getSongListCount,
  updateSongList,
} from "@/api/SongList";

export default {
  name: "SongList",
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
        title: "",
        introduction: "",
        pic: "",
        style: "",
      },
      form: {
        id: "",
        title: "",
        introduction: "",
        pic: "",
        style: "",
      },
      tableData: [],
      select_word: "",
      pageSize: 4,
      currentPage: 1,
      songListCount: 0,
      rules: {
        title: [{ required: true, message: "请输入标题", trigger: "blur" }],
        introduction: [
          { required: true, message: "请输入简介", trigger: "blur" },
        ],
        style: [{ required: true, message: "请输入风格", trigger: "blur" }],
        pic: [{ required: true, message: "请上传图片", trigger: "blur" }],
      },
    };
  },
  watch: {
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
    currentPage(val) {
      if (val) {
        this.getAllData();
      }
    },
  },
  created() {
    this.getAllData();
  },
  methods: {
    goComment(id) {
      this.$router.push({ path: "/comment", query: { songListId: id } });
    },
    songEdit(id) {
      this.$router.push({ path: "/ListSong", query: { id } });
    },
    // 切换播放音乐
    setSongUrl(url, name) {
      this.toggle = name;
      this.$store.commit("setUrl", url);
      if (this.isPlay) {
        this.toggle = false;
        this.$store.commit("setIsPlay", false);
      } else {
        this.$store.commit("setIsPlay", true);
      }
    },
    handleDelAll() {
      this.delAllVisible = true;
    },
    // 批量删除
    delAll() {
      let ids = this.multipleSelection;
      deleteAllSongList(ids).then((res) => {
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
      deleteSongList(id).then((res) => {
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
      let SongList = this.form;
      updateSongList(SongList).then((res) => {
        if (res.code === 200) {
          this.editVisible = false;
          this.$message.success(res.msg);
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
      getSongListByName(name).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        } else {
          this.tableData = [];
        }
      });
    },
    getAllData() {
      getAllSongListByPage(this.currentPage).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        }
      });
      getSongListCount().then((res) => {
        if (res.code === 200) {
          this.songListCount = res.data;
        }
      });
    },
    // 添加歌手
    addSave() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          let SongList = this.registerForm;
          addSongList(SongList).then((res) => {
            if (res.code === 200) {
              this.$message.success(res.msg);
              this.getAllData();
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
        this.$message.error("上传头像图片只能是 JPG 格式!");
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
.song-img {
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
