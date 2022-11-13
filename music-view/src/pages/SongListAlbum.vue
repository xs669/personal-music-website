<template>
  <div>
    <div class="song-list-album">
      <div class="album-slide">
        <div class="album-img">
          <img :src="tempList.pic" alt="歌单图片" />
        </div>
        <div class="album-info">
          <h2>简介：</h2>
          <span>{{ tempList.introduction }}</span>
        </div>
      </div>
      <div class="album-content">
        <div class="album-title">
          <p>{{ tempList.title }}</p>
        </div>
        <div class="album-score">
          <div>
            <h3>歌单评分：</h3>
            <div>
              <el-rate v-model="avg" disabled></el-rate>
            </div>
          </div>
          <span>{{ avg * 2 }}</span>
          <div>
            <h3>评价：</h3>
            <div @click="addRankScore">
              <el-rate v-model="rank" allow-half show-text></el-rate>
            </div>
          </div>
          <div>
            <h3>收藏</h3>
            <div ref="collect" @click="collect" style="cursor: pointer">
              <svg class="icon">
                <use xlink:href="#icon-xihuan-shi"></use>
              </svg>
            </div>
          </div>
        </div>
        <div class="songs-body">
          <album-content :song-lists="listOfSongs">
            <template slot="title">歌单</template>
          </album-content>
          <comment :play-id="songListId" :type="1"></comment>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixins } from "@/mixins";
import AlbumContent from "../components/AlbumContent";
import { addRank, getRankAvgScore } from "@/api/Rank";
import Comment from "../components/Comment";
import {
  addCollect,
  cancelCollect,
  getCollectBySongListId,
} from "@/api/Collect";

export default {
  name: "SongListAblum",
  mixins: [mixins],
  components: { Comment, AlbumContent },
  data() {
    return {
      songListId: "",
      avg: 0,
      rank: 0,
      liked: false,
    };
  },
  computed: {
    ...mapGetters(["listOfSongs", "tempList", "loginIn", "userId"]),
  },
  watch: {
    loginIn(val) {
      if (!val) {
        this.liked = false;
        this.$refs.collect.children[0].style.color = "#696969";
      } else {
        this.getSongCollectState();
      }
    },
  },
  created() {
    this.songListId = this.$route.query.id;
    this.getSongListDetailById();
    this.getRankScore();
    this.getSongCollectState();
  },
  methods: {
    getRankScore() {
      getRankAvgScore(this.songListId).then((res) => {
        if (res.data.code === 200) {
          this.avg = parseFloat(res.data.data);
        }
      });
    },
    addRankScore() {
      let Rank = {
        songListId: this.songListId,
        consumerId: this.userId,
        score: this.rank * 2,
      };
      if (this.loginIn && localStorage.getItem("consumerName")) {
        addRank(Rank).then((res) => {
          if (res.data.code === 200) {
            this.$message.success(res.data.msg);
            this.getRankScore();
            this.rank = 0;
          } else {
            this.rank = 0;
            this.$message.error(res.data.msg);
          }
        });
      } else {
        this.rank = 0;
        this.$message.warning("用户未登录, 不能评价歌单");
      }
    },
    getSongCollectState() {
      let CollectVo = {
        userId: this.userId,
        type: 1,
        songId: "",
        songListId: this.songListId,
      };
      if (localStorage.getItem("consumerName")) {
        getCollectBySongListId(CollectVo).then((res) => {
          if (res.data.code === 200) {
            this.liked = true;
            this.$refs.collect.children[0].style.color = "#DC143C";
          } else {
            this.liked = false;
            this.$refs.collect.children[0].style.color = "#696969";
          }
        });
      }
    },
    collect() {
      let Collect = {
        userId: this.userId,
        type: 1,
        songId: "",
        songListId: this.songListId,
      };
      if (localStorage.getItem("consumerName")) {
        if (!this.liked) {
          addCollect(Collect).then((res) => {
            if (res.data.code === 200) {
              this.liked = true;
              this.$refs.collect.children[0].style.color = "#DC143C";
              this.getUserCollectData();
              this.$message.success(res.data.msg);
            } else {
              this.getUserCollectData();
            }
          });
        } else {
          cancelCollect(Collect).then((res) => {
            if (res.data.code === 200) {
              this.liked = false;
              this.$refs.collect.children[0].style.color = "#696969";
              this.getUserCollectData();
              this.$message.success(res.data.msg);
            } else {
              this.getUserCollectData();
            }
          });
        }
      } else {
        this.$message.warning("未登录, 不能收藏");
      }
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/song-list-album";
</style>
