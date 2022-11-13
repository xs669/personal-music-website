<template>
  <div class="song-list">
    <ul class="song-list-header">
      <li
        v-for="(item, index) in songStyle"
        :key="index"
        @click="handleChangeView(item.name)"
        :class="{ active: item.name === activeName }"
      >
        {{ item.name }}
      </li>
    </ul>
    <div>
      <content-list :content-lists="albumDatas"></content-list>
      <div class="pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          background
          layout="total,prev,pager,next"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="songListCount"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { mixins } from "@/mixins";
import ContentList from "../components/ContentList";
import {
  getAllSongListByPage,
  getAllSongListCountByStyle,
  getAllStyle,
  getSongListByStyleAndPage,
  getSongListCount,
} from "@/api/SongList";

export default {
  name: "SongList",
  mixins: [mixins],
  components: { ContentList },
  data() {
    return {
      albumDatas: [],
      pageSize: 10,
      currentPage: 1,
      songStyle: [],
      activeName: "",
      songListCount: 0,
    };
  },
  created() {
    this.getAllStyle();
    this.getAllData();
  },
  mounted() {
    this.activeName = "全部歌单";
  },
  watch: {
    currentPage(val) {
      if (val) {
        this.handleChangeView(this.activeName);
      }
    },
    activeName() {
      this.currentPage = 1;
    },
  },
  methods: {
    getAllStyle() {
      getAllStyle().then((res) => {
        if (res.data.code === 200) {
          let style = res.data.data;
          this.songStyle.push({ name: "全部歌单" });
          for (let index in style) {
            this.songStyle.push({ name: style[index].style });
          }
        }
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    getAllData() {
      getAllSongListByPage(this.currentPage).then((res) => {
        if (res.data.code === 200) {
          this.albumDatas = res.data.data;
        }
      });
      getSongListCount().then((res) => {
        if (res.data.code === 200) {
          this.songListCount = res.data.data;
        }
      });
    },
    handleChangeView(name) {
      this.albumDatas = [];
      if (name !== "全部歌单") {
        this.activeName = name;
        getSongListByStyleAndPage(name, this.currentPage).then((res) => {
          if (res.data.code === 200) {
            this.albumDatas = res.data.data;
          }
        });
        getAllSongListCountByStyle(name).then((res) => {
          if (res.data.code === 200) {
            this.songListCount = res.data.data;
          }
        });
      } else {
        this.activeName = name;
        this.getAllData();
      }
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/song-list";
</style>
