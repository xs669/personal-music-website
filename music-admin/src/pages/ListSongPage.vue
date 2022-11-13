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
          placeholder="请输入关键词"
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
      <el-table-column
        prop="singerNameAndsongName"
        label="歌手-歌曲"
        align="center"
      >
        <template v-slot="scope">
          {{ scope.row.singerNameAndsongName }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150px" align="center">
        <template v-slot="scope">
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
        ref="registerForm"
        :rules="rules"
        label-width="80px"
      >
        <el-form-item prop="singerName" label="歌手名称" size="mini">
          <el-select
            v-model="registerForm.singerName"
            placeholder="请选择歌手名称"
          >
            <el-option
              v-for="item in options1"
              :key="item.id"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="songName" label="歌曲名称" size="mini">
          <el-select
            v-model="registerForm.songName"
            placeholder="请选择歌曲名称"
          >
            <el-option
              v-for="item in options2"
              :key="item.id"
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
  addListSong,
  deleteAllListSong,
  deleteListSong,
  getAllListSongBySongListId,
  getListSongByName,
  getSongNameBySingerId,
} from "@/api/ListSong";
import { getAllSinger } from "@/api/Singer";

export default {
  name: "ListSong",
  data() {
    return {
      centerDialogVisible: false, // 新增弹窗
      delVisible: false, // 删除窗口
      delAllVisible: false, // 批量删除窗口
      idx: "", // 删除行id
      control: true, // 控制按钮禁用状态
      multipleSelection: [], // 勾选项
      singerId: "",
      registerForm: {
        singerName: "",
        songName: "",
      },
      temp1: [],
      temp2: [],
      form: {
        songId: "",
        songListId: "",
        singerNameAndsongName: "",
      },
      tableData: [],
      select_word: "",
      pageSize: 11,
      currentPage: 1,
      options1: [],
      options2: [],
      rules: {
        singerName: [
          { required: true, message: "请选择歌手名称", trigger: "blur" },
        ],
        songName: [
          { required: true, message: "请选择歌曲名称", trigger: "blur" },
        ],
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
      if (val) {
        let count = 0;
        getAllSinger().then((res) => {
          if (res.code === 200) {
            let SingerData = res.data;
            for (let index in SingerData) {
              this.options1.push({
                id: SingerData[index].id,
                value: (count += 1),
                label: SingerData[index].name,
              });
            }
          }
        });
      } else {
        this.registerForm.singerName = "";
        this.registerForm.songName = "";
        this.options1 = [];
      }
    },
    "registerForm.singerName": {
      deep: true,
      handler(newVal, oldVal) {
        if (newVal !== "") {
          if (oldVal !== "") {
            this.options2 = [];
            this.getSongListData();
          } else {
            this.getSongListData();
          }
        } else {
          this.options2 = [];
        }
      },
    },
  },
  computed: {
    data() {
      let begin = (this.currentPage - 1) * this.pageSize;
      let end = this.currentPage * this.pageSize;
      return this.tableData.slice(begin, end);
    },
  },
  created() {
    this.getAllData();
  },
  methods: {
    getSongListData() {
      let count = 0;
      for (let index in this.options1) {
        if (this.registerForm.singerName === this.options1[index].label) {
          this.singerId = this.options1[index].id;
          break;
        }
      }
      getSongNameBySingerId(this.singerId).then((res) => {
        if (res.code === 200) {
          let SongData = res.data;
          for (let index in SongData) {
            this.options2.push({
              id: SongData[index].songId,
              value: (count += 1),
              label: SongData[index].songName,
            });
          }
        }
      });
    },
    handleDelAll() {
      this.delAllVisible = true;
    },
    // 批量删除
    delAll() {
      let ids = this.multipleSelection;
      deleteAllListSong(ids).then((res) => {
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
      deleteListSong(id).then((res) => {
        if (res.code === 200) {
          this.delVisible = false;
          this.$message.success(res.msg);
          this.getAllData();
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    search() {
      let ListSongByNameDto = {
        songListId: this.$route.query.id,
        name: this.select_word,
      };
      getListSongByName(ListSongByNameDto).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        } else {
          this.tableData = [];
        }
      });
    },
    getAllData() {
      let id = this.$route.query.id;
      getAllListSongBySongListId(id).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        }
      });
    },
    // 添加歌单歌曲
    addSave() {
      this.$refs.registerForm.validate((valid) => {
        if (valid) {
          for (let index in this.options2) {
            if (this.registerForm.songName === this.options2[index].label) {
              this.form.songId = this.options2[index].id;
              break;
            }
          }
          this.form.songListId = this.$route.query.id;
          this.form.singerNameAndsongName =
            this.registerForm.singerName + "-" + this.registerForm.songName;
          let listSongVo = this.form;
          addListSong(listSongVo).then((res) => {
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
.handle-input {
  width: 300px;
  display: inline-block;
}
.pagination {
  display: flex;
  justify-content: center;
}
</style>
