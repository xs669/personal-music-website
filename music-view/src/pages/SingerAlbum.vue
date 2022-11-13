<template>
  <div class="singer-album">
    <div class="album-slide">
      <div class="singer-img">
        <img :src="singer.pic" alt="歌手头像" />
      </div>
      <ul class="info">
        <li v-if="singer.sex === 0 || singer.sex === 1">
          {{ attachSingerSex(singer.sex) }}
        </li>
        <li>生日：{{ singer.birth.slice(0, 10) }}</li>
        <li>故乡：{{ singer.location }}</li>
      </ul>
    </div>
    <div class="album-content">
      <div class="intro">
        <h2>{{ singer.name }}</h2>
        <span>{{ sliceStr }}</span>
      </div>
      <div class="content">
        <album-content :song-lists="listOfSongs">
          <template slot="title">歌曲</template>
        </album-content>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixins } from "@/mixins";
import AlbumContent from "../components/AlbumContent";

export default {
  name: "SingerAlbum",
  mixins: [mixins],
  components: { AlbumContent },
  data() {
    return {
      singerId: "",
      singer: {},
      textShow: false,
      textIf: true,
    };
  },
  computed: {
    ...mapGetters(["tempList", "listOfSongs", "loginIn", "userId"]),
    sliceStr() {
      if (
        this.singer.introduction === "" ||
        this.singer.introduction === undefined
      ) {
        return this.singer.introduction; // 数据为空直接返回数据
      } else {
        if (this.singer.introduction.length > 200) {
          return this.singer.introduction.substring(0, 200) + "..."; // 隐藏多余文字 + "..."
        }
      }
      return this.singer.introduction;
    },
  },
  created() {
    this.singerId = this.$route.query.id;
    this.singer = this.tempList;
    this.getSongOfSingerId();
  },
  methods: {
    attachSingerSex(value) {
      if (value === 0) {
        return "女";
      }
      if (value === 1) {
        return "男";
      }
      return "";
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/singer-album";
</style>
