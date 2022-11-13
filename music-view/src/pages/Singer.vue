<template>
  <div class="singer">
    <ul class="singer-header">
      <li
        v-for="(item, index) in singerStyle"
        :key="index"
        @click="handleChangeView(item.name, item.type)"
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
          :total="singerCount"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { mixins } from "@/mixins";
import singerStyle from "../assets/data/singer";
import ContentList from "../components/ContentList";
import {
  getAllSingerBySexAndPage,
  getAllSingerCountBySex,
  getSingerByPage,
  getSingerCount,
} from "@/api/Singer";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Singer",
  components: { ContentList },
  mixins: [mixins],
  data() {
    return {
      albumDatas: [],
      pageSize: 10,
      currentPage: 1,
      singerStyle: [],
      activeName: "",
      singerCount: 0,
      type: -1,
    };
  },
  mounted() {
    this.singerStyle = singerStyle;
    this.activeName = "全部歌手";
  },
  created() {
    this.getSinger();
  },
  watch: {
    currentPage(val) {
      if (val) {
        if (this.type !== -1) {
          this.getSingerBySex();
        } else {
          this.getSinger();
        }
      }
    },
    type(val) {
      this.currentPage = 1;
      if (val !== -1) {
        this.getSingerBySex();
      } else {
        this.getSinger();
      }
    },
  },
  methods: {
    getSinger() {
      getSingerByPage(this.currentPage).then((res) => {
        if (res.data.code === 200) {
          this.albumDatas = res.data.data;
        }
      });
      getSingerCount().then((res) => {
        if (res.data.code === 200) {
          this.singerCount = res.data.data;
        }
      });
    },
    getSingerBySex() {
      getAllSingerBySexAndPage(this.type, this.currentPage).then((res) => {
        if (res.data.code === 200) {
          this.albumDatas = res.data.data;
        }
      });
      getAllSingerCountBySex(this.type).then((res) => {
        if (res.data.code === 200) {
          this.singerCount = res.data.data;
        }
      });
    },
    handleCurrentChange(val) {
      this.currentPage = val;
    },
    handleChangeView(name, type) {
      this.albumDatas = [];
      this.type = type;
      this.activeName = name;
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/singer";
</style>
