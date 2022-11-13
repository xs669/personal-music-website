<template>
  <div class="swiper">
    <el-carousel :interval="4000" type="card" height="280px">
      <el-carousel-item v-for="(item, index) in swiperList" :key="index">
        <img
          style="width: 637px"
          :src="item.picImg"
          alt="图片"
          @click="goSongList(index)"
        />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
import swiperList from "../assets/data/swiper";
import { getAllSongList } from "@/api/SongList";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Swiper",
  data() {
    return {
      swiperList: [],
    };
  },
  created() {
    this.swiperList = swiperList;
  },
  methods: {
    goSongList(index) {
      getAllSongList().then((res) => {
        if (res.data.code === 200) {
          let songListData = res.data.data;
          this.$store.commit("setTempList", songListData[index]);
          this.$router.push({
            path: "/songListAlbum",
            query: { id: songListData[index].id },
          });
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">
@import url(../assets/css/swiper.scss);
</style>
