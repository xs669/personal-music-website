<template>
  <div class="home">
    <swiper />
    <div class="section" v-for="(item, index) in songLists" :key="index">
      <div v-show="!consumerName" class="section-title">{{ item.name }}</div>
      <div v-show="consumerName" class="section-title">
        {{ "精选" + item.name }}
      </div>
      <content-list
        v-show="!consumerName"
        :contentLists="item.list"
      ></content-list>
      <content-list
        v-show="consumerName"
        :contentLists="item.list"
      ></content-list>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Swiper from "../components/Swiper";
import { getAllSongList } from "@/api/SongList";
import { getAllSinger } from "@/api/Singer";
import ContentList from "../components/ContentList";
import { recommendSinger, recommendSongList } from "@/api/RecentSong";

function unique(arr) {
  return arr.filter(function (item, index, arr) {
    // 当前元素，在原始数组中的第一个索引==当前索引值，否则返回当前元素
    return arr.indexOf(item, 0) === index;
  });
}

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "home",
  components: {
    ContentList,
    Swiper,
  },
  data() {
    return {
      songLists: [
        { name: "歌单", list: [] },
        { name: "歌手", list: [] },
      ],
      singerIndex: [],
      songListIndex: [],
      consumerName: "",
      tempSongList: [],
      tempSinger: [],
    };
  },
  computed: {
    ...mapGetters(["userId", "url"]),
  },
  created() {
    this.consumerName = localStorage.getItem("consumerName");
    this.getDataByUser();
  },
  watch: {
    userId() {
      this.consumerName = localStorage.getItem("consumerName");
      this.songLists[0].list = [];
      this.songLists[1].list = [];
      this.getDataByUser();
    },
    url() {
      if (localStorage.getItem("consumerName")) {
        this.songLists[0].list = [];
        this.songLists[1].list = [];
        this.getDataByUser();
      }
    },
  },
  methods: {
    getDataByUser() {
      if (!this.consumerName) {
        this.getSongLists();
        this.getSingers();
      } else {
        this.getRecommendData();
      }
    },
    getRecommendData() {
      recommendSongList(this.userId).then((res) => {
        if (res.data.code === 200) {
          let songListData = res.data.data;
          for (let i = 0; i < songListData.length * 5; i++) {
            this.songListIndex.push(
              parseInt(Math.random() * songListData.length, 10)
            );
          }
          this.songListIndex = unique(this.songListIndex).slice(0, 10);
          for (let songListIndex of this.songListIndex) {
            this.songLists[0].list.push(songListData[songListIndex]);
          }
        }
      });
      recommendSinger(this.userId).then((res) => {
        if (res.data.code === 200) {
          let singerData = res.data.data;
          for (let i = 0; i < singerData.length * 5; i++) {
            this.singerIndex.push(
              parseInt(Math.random() * singerData.length, 10)
            );
          }
          this.singerIndex = unique(this.singerIndex).slice(0, 10);
          for (let singerIndex of this.singerIndex) {
            this.songLists[1].list.push(singerData[singerIndex]);
          }
        }
      });
    },
    getSongLists() {
      getAllSongList().then((res) => {
        if (res.data.code === 200) {
          let songListData = res.data.data;
          for (let i = 0; i < songListData.length * 5; i++) {
            this.songListIndex.push(
              parseInt(Math.random() * songListData.length, 10)
            );
          }
          this.songListIndex = unique(this.songListIndex).slice(0, 10);
          for (let songListIndex of this.songListIndex) {
            this.songLists[0].list.push(songListData[songListIndex]);
          }
        }
      });
    },
    getSingers() {
      getAllSinger().then((res) => {
        if (res.data.code === 200) {
          let singerData = res.data.data;
          for (let i = 0; i < singerData.length * 5; i++) {
            this.singerIndex.push(
              parseInt(Math.random() * singerData.length, 10)
            );
          }
          this.singerIndex = unique(this.singerIndex).slice(0, 10);
          for (let singerIndex of this.singerIndex) {
            this.songLists[1].list.push(singerData[singerIndex]);
          }
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../assets/css/home.scss";
</style>
