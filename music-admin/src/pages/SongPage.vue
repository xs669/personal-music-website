<template>
  <div class="table">
    <div class="crumbs">
      <i class="el-icon-tickets"></i>
      歌曲信息
    </div>
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
          placeholder="请输入歌曲名"
          class="handle-input"
          @keyup.enter.native="search"
        ></el-input>
        <el-button
          type="primary"
          size="mini"
          @click="centerDialogVisible = true"
          >添加歌曲</el-button
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
      <el-table-column label="歌曲图片" width="110px" align="center">
        <template v-slot="scope">
          <div class="song-img">
            <img :src="scope.row.pic" style="width: 100%" alt="歌曲图片" />
          </div>
          <div class="play" @click="setSongUrl(scope.row.url)">
            <div v-if="toggle !== scope.row.url">
              <svg class="icon">
                <use xlink:href="#icon-bofanganniu"></use>
              </svg>
            </div>
            <div v-if="toggle === scope.row.url">
              <svg class="icon">
                <use xlink:href="#icon-zanting"></use>
              </svg>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="歌名"
        width="120px"
        align="center"
      ></el-table-column>
      <el-table-column
        prop="introduction"
        label="专辑"
        width="150px"
        align="center"
      ></el-table-column>
      <el-table-column label="歌词" align="center">
        <template v-slot="scope">
          <ul style="height: 100px; overflow: scroll">
            <li v-for="(item, index) in parseLylic(scope.row)" :key="index">
              {{ item }}
            </li>
          </ul>
        </template>
      </el-table-column>
      <el-table-column label="评论" width="80px" align="center">
        <template v-slot="scope">
          <el-button size="mini" type="primary" @click="goComment(scope.row.id)"
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
      title="添加歌曲"
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
        <el-form-item prop="name" label="歌名" size="mini">
          <el-input v-model="registerForm.name" placeholder="歌名"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="专辑" size="mini">
          <el-input
            v-model="registerForm.introduction"
            placeholder="专辑"
          ></el-input>
        </el-form-item>
        <el-form-item prop="pic" label="歌曲图片" size="mini">
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
              v-if="registerForm.pic"
              :src="registerForm.pic"
              class="avatar"
              alt="头像"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item prop="lyric" label="歌词" size="mini">
          <el-input
            type="textarea"
            v-model="registerForm.lyric"
            placeholder="歌词"
          ></el-input>
        </el-form-item>
        <el-form-item prop="url" label="歌曲地址" size="mini">
          <el-upload
            class="upload-demo"
            action="http://localhost:8888/uploadAudio"
            name="mp3"
            :multiple="true"
            :auto-upload="true"
            :show-file-list="true"
            :on-success="handleAudioSuccess"
            :before-upload="beforeAudioUpload"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button
          size="mini"
          type="primary"
          @click="centerDialogVisible = false"
          >取消</el-button
        >
        <el-button size="mini" type="primary" @click="addSong">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="修改歌曲"
      :visible.sync="editVisible"
      width="400px"
      center
    >
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item prop="name" label="歌手id" size="mini">
          <el-input v-model="form.singerId" disabled></el-input>
        </el-form-item>
        <el-form-item prop="name" label="歌名" size="mini">
          <el-input v-model="form.name" placeholder="歌名"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="专辑" size="mini">
          <el-input v-model="form.introduction" placeholder="专辑"></el-input>
        </el-form-item>
        <el-form-item prop="pic" label="歌曲图片" size="mini">
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
              v-if="form.pic"
              :src="form.pic"
              class="avatar"
              alt="歌曲图片"
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item prop="lyric" label="歌词" size="mini">
          <el-input
            type="textarea"
            v-model="form.lyric"
            placeholder="歌词"
          ></el-input>
        </el-form-item>
        <el-form-item prop="url" label="歌曲地址" size="mini">
          <el-upload
            class="upload-demo"
            action="http://localhost:8888/uploadAudio"
            name="mp3"
            :multiple="true"
            :auto-upload="true"
            :show-file-list="true"
            :on-success="updateAudioSuccess"
            :before-upload="beforeAudioUpload"
          >
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="playCount" label="播放量" size="mini">
          <el-input v-model="form.playCount" placeholder="播放量"></el-input>
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
import "../assets/js/iconfont";
import {
  addSong,
  deleteAllSong,
  deleteSong,
  getAllSongByNameAndSingerId,
  getAllSongBySingerId,
  updateSong,
} from "@/api/Song";
import { mapGetters } from "vuex";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Song",
  data() {
    return {
      centerDialogVisible: false, // 新增弹窗
      editVisible: false, // 编辑弹窗
      delVisible: false, // 删除窗口
      delAllVisible: false, // 批量删除窗口
      idx: "", // 删除行id
      control: true, // 控制按钮禁用状态
      multipleSelection: [], // 勾选项
      singerId: "",
      singerName: "",
      toggle: false, // 播放器的图标状态
      registerForm: {
        singerId: "",
        name: "",
        introduction: "",
        pic: "",
        lyric: "",
        url: "",
        playCount: 0,
      },
      form: {
        id: "",
        singerId: "",
        name: "",
        introduction: "",
        pic: "",
        lyric: "",
        url: "",
        playCount: 0,
      },
      tableData: [],
      select_word: "",
      pageSize: 4,
      currentPage: 1,
      rules: {
        name: [{ required: true, message: "请输入歌曲名称", trigger: "blur" }],
        introduction: [
          { required: true, message: "请输入歌曲专辑", trigger: "blur" },
        ],
        lyric: [{ required: true, message: "请输入歌词", trigger: "blur" }],
        pic: [{ required: true, message: "请上传歌曲图片", trigger: "blur" }],
        url: [{ required: true, message: "请上传歌曲", trigger: "blur" }],
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
    isPlay(val) {
      if (!val) {
        this.toggle = false;
      }
    },
  },
  created() {
    this.singerId = this.$route.query.id;
    this.singerName = this.$route.query.name;
    this.getAllData();
  },
  destroyed() {
    this.$store.commit("setIsPlay", false);
  },
  computed: {
    data() {
      let begin = (this.currentPage - 1) * this.pageSize;
      let end = this.currentPage * this.pageSize;
      return this.tableData.slice(begin, end);
    },
    ...mapGetters(["id", "url", "isPlay"]),
  },
  methods: {
    goComment(id) {
      this.$router.push({ path: "/comment", query: { songId: id } });
    },
    // 切换播放音乐
    setSongUrl(url) {
      this.$store.commit("setUrl", url);
      if (this.isPlay) {
        this.$store.commit("setIsPlay", false);
        this.toggle = false;
      } else {
        this.$store.commit("setIsPlay", true);
        this.toggle = url;
      }
    },
    // 歌词解析
    parseLylic(row) {
      let result = [];
      let lines = row.lyric.split("\n");
      for (let item in lines) {
        let value = lines[item].split("]")[1];
        result.push(value);
      }
      return result;
    },
    handleDelAll() {
      this.delAllVisible = true;
    },
    // 批量删除
    delAll() {
      let ids = this.multipleSelection;
      deleteAllSong(ids).then((res) => {
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
      deleteSong(id).then((res) => {
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
      let Song = this.form;
      updateSong(Song).then((res) => {
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
      let id = this.$route.query.id;
      let name = this.select_word;
      let SongVo = [
        {
          id: id,
          name: name,
        },
      ];
      getAllSongByNameAndSingerId(SongVo).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        } else {
          this.tableData = [];
        }
      });
    },
    getAllData() {
      this.tableData = [];
      let id = this.$route.query.id;
      getAllSongBySingerId(id).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        }
      });
    },
    // 添加歌曲
    addSong() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          this.registerForm.singerId = this.singerId;
          let Song = this.registerForm;
          addSong(Song).then((res) => {
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
    updateAudioSuccess(res) {
      if (res.code === 20000) {
        this.form.url = res.data;
        this.$message.success(res.message);
      } else {
        this.$message.error(res.message);
      }
    },
    handleAudioSuccess(res) {
      if (res.code === 20000) {
        this.registerForm.url = res.data;
        this.$message.success(res.message);
      } else {
        this.$message.error(res.message);
      }
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
    beforeAudioUpload(file) {
      const isMP3 = file.type === "audio/mpeg";
      if (!isMP3) {
        this.$message.warning("上传音频只能是MP3格式！");
      }
      return isMP3;
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
.play {
  position: absolute;
  z-index: 100;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  top: 18px;
  left: 15px;
}
.icon {
  width: 2em;
  height: 2em;
  color: white;
  fill: currentColor;
  overflow: hidden;
}
</style>
