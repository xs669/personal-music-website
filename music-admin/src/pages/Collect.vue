<template>
  <div class="table">
    <div class="container">
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
          <div class="collect-img">
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
      <el-table-column prop="introduction" label="收藏内容" align="center">
        <template v-slot="scope">
          <p style="height: 100px; overflow: scroll">
            {{ scope.row.content }}
          </p>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="收藏时间"
        width="120px"
        align="center"
      >
        <template v-slot="scope">
          {{ scope.row.createTime.slice(0, 10) }}
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
  delAllCollect,
  delCollect,
  getAllCollectByUserId,
  searchCollectByUserId,
} from "@/api/Collect";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Collect",
  data() {
    return {
      delVisible: false, // 删除窗口
      delAllVisible: false, // 批量删除窗口
      id: "", // 删除行id
      control: true, // 控制按钮禁用状态
      multipleSelection: [], // 勾选项
      tableData: [],
      select_word: "",
      pageSize: 4,
      currentPage: 1,
      userId: "",
    };
  },
  watch: {
    select_word(val) {
      if (val === "") {
        this.getAllCollect();
      }
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
    this.userId = this.$route.query.userId;
    this.getAllCollect();
  },
  methods: {
    handleDelAll() {
      this.delAllVisible = true;
    },
    // 批量删除
    delAll() {
      delAllCollect(this.multipleSelection).then((res) => {
        if (res.code === 200) {
          this.$message.success(res.msg);
          this.delAllVisible = false;
          this.getAllCollect();
        }
      });
    },
    // 选中数据
    handleSelectionChange(val) {
      if (val.length === 0) {
        this.multipleSelection = [];
        this.control = true;
      } else {
        for (let data in val) {
          this.multipleSelection.push(val[data].id);
          this.control = false;
        }
      }
    },
    handleDelete(id) {
      this.id = id;
      this.delVisible = true;
    },
    // 删除功能
    deleteRow() {
      delCollect(this.id).then((res) => {
        if (res.code === 200) {
          this.$message.success(res.msg);
          this.delVisible = false;
          this.getAllCollect();
        }
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    search() {
      let searchDto = {
        id: this.userId,
        keyWord: this.select_word,
      };
      searchCollectByUserId(searchDto).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        }
      });
    },
    getAllCollect() {
      getAllCollectByUserId(this.userId).then((res) => {
        if (res.code === 200) {
          this.tableData = res.data;
        }
      });
    },
  },
};
</script>

<style scoped>
.collect-img {
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
