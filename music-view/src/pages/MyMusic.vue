<template>
  <div class="my-music">
    <div class="album-slide">
      <div class="album-img">
        <img :src="avatar" alt="用户头像" />
      </div>
      <ul class="album-info">
        <li>昵称：{{ username }}</li>
        <li>性别：{{ sex }}</li>
        <li>生日：{{ birth }}</li>
        <li>故乡：{{ location }}</li>
      </ul>
    </div>
    <div class="album-content">
      <div class="album-title">个性签名：{{ introduction }}</div>
      <div class="songs-body">
        <ul class="content">
          <li
            v-for="(item, index) in List"
            :key="index"
            :class="{ activeColor: activeName === item.name }"
            @click="changeName(item.name)"
          >
            {{ item.name }}
          </li>
        </ul>
      </div>
      <div style="margin-top: 20px">
        <album-content
          :song-lists="collectList.slice(0, 10)"
          v-show="activeName === '收藏歌曲'"
        ></album-content>
        <album-content
          :song-lists="recentSongList.slice(0, 10)"
          v-show="activeName === '最近播放'"
        ></album-content>
        <div class="songList">
          <content-list
            :content-lists="songList.slice(0, 10)"
            v-show="activeName === '收藏歌单'"
          ></content-list>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mixins } from "@/mixins";
import { mapGetters } from "vuex";
import { getConsumerById } from "@/api/Consumer";
import AlbumContent from "../components/AlbumContent";
import { getRecentSongByUserId } from "@/api/RecentSong";
import ContentList from "../components/ContentList";
import { getAllCollectSongListByUserId } from "@/api/Collect";

export default {
  name: "MyMusic",
  components: { ContentList, AlbumContent },
  mixins: [mixins],
  data() {
    return {
      avatar: "",
      username: "",
      sex: "",
      birth: "",
      location: "",
      introduction: "",
      songList: [],
      List: [
        { name: "收藏歌曲", path: "" },
        { name: "最近播放", path: "" },
        { name: "收藏歌单", path: "" },
      ],
      activeName: "收藏歌曲",
    };
  },
  computed: {
    ...mapGetters([
      "listOfSongs",
      "userId",
      "collectList",
      "recentSongList",
      "url",
    ]),
  },
  created() {
    this.getUserData();
    this.getUserCollectData();
    this.getRecentSongByUserId();
    this.getAllCollectSongListByUserId();
  },
  watch: {
    url() {
      if (localStorage.getItem("consumerName")) {
        this.getRecentSongByUserId(this.userId);
      }
    },
  },
  methods: {
    getAllCollectSongListByUserId() {
      getAllCollectSongListByUserId(this.userId).then((res) => {
        if (res.data.code === 200) {
          this.songList = res.data.data;
        }
      });
    },
    changeName(name) {
      this.activeName = name;
    },
    getRecentSongByUserId() {
      getRecentSongByUserId(this.userId).then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("setRecentSongList", res.data.data);
        }
      });
    },
    getUserData() {
      getConsumerById(this.userId).then((res) => {
        if (res.data.code === 200) {
          let Consumer = res.data.data;
          this.avatar = Consumer.avatar;
          this.username = Consumer.username;
          this.sex = Consumer.sex === 1 ? "男" : "女";
          this.birth = Consumer.birth.slice(0, 10);
          this.location = Consumer.location;
          this.introduction = Consumer.introduction;
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/my-music";
</style>
