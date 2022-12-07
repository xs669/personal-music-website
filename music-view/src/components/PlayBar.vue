<template>
  <div class="play-bar" :class="{ show: !toggleView }">
    <div class="item-up" @click="toggleView = !toggleView">
      <svg class="icon" :class="{ turn: !toggleView }">
        <use xlink:href="#icon-jiantou-xia-cuxiantiao"></use>
      </svg>
    </div>
    <div class="kongjian">
      <div class="item" @click="prev">
        <svg class="icon">
          <use xlink:href="#icon-shangyishouge"></use>
        </svg>
      </div>
      <div class="item" @click="togglePlay">
        <svg class="icon">
          <use :xlink:href="playButtonUrl"></use>
        </svg>
      </div>
      <div class="item" @click="next">
        <svg class="icon">
          <use xlink:href="#icon-kuaijin"></use>
        </svg>
      </div>
      <div class="item">
        <img
          :src="picUrl"
          alt="歌曲图片"
          style="width: 50px; height: 50px"
          @click="toLyric"
        />
      </div>
      <div class="playing-speed">
        <div class="current-time">{{ nowTime }}</div>
        <div class="progress-box">
          <div class="item-song-title">
            <div>{{ title }}</div>
            <div>{{ artist }}</div>
          </div>
          <div
            class="progress"
            ref="progress"
            @mousemove="mousemove"
            style="cursor: pointer"
          >
            <div ref="bg" class="bg" @click="updatemove">
              <div
                ref="curProgress"
                class="cur-progress"
                :style="{ width: curLength + '%' }"
              ></div>
            </div>
            <div
              class="idot"
              ref="idot"
              :style="{ left: curLength + '%' }"
              @mousedown="mousedown"
              @mouseup="mouseup"
            ></div>
          </div>
        </div>
        <div class="left-time">{{ songTime }}</div>
        <div
          class="item"
          @click="changePlayState"
          @mouseenter="changeActive"
          @mouseleave="removeActive"
        >
          <div ref="info" class="info">
            <span>{{ info }}</span>
          </div>
          <svg class="play">
            <use :xlink:href="control"></use>
          </svg>
        </div>
        <div class="item volume" @click="showVolume">
          <svg v-if="volume === 0" class="icon">
            <use xlink:href="#icon-yinliangjingyinheix"></use>
          </svg>
          <svg v-else class="icon">
            <use xlink:href="#icon-yinliang1"></use>
          </svg>
          <el-slider
            v-if="hide"
            class="volume"
            v-model="volume"
            :vertical="true"
          ></el-slider>
        </div>
        <div class="item" @click="collect" ref="like">
          <svg class="icon">
            <use xlink:href="#icon-xihuan-shi"></use>
          </svg>
        </div>
        <div class="item" @click="downloadMP3">
          <svg class="icon">
            <use xlink:href="#icon-xiazai"></use>
          </svg>
        </div>
        <div class="item" @click="changeAside">
          <svg class="icon">
            <use xlink:href="#icon-liebiao"></use>
          </svg>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixins } from "@/mixins";

import {
  addCollect,
  cancelCollect,
  getAllCollectByConsumerId,
  getCollectBySongId,
} from "@/api/Collect";
import axios from "axios";

export default {
  name: "PlayBar",
  mixins: [mixins],
  data() {
    return {
      nowTime: "00:00",
      songTime: "00:00",
      curLength: 0,
      progressLength: 0,
      mouseStartX: 0,
      tag: false,
      volume: 50,
      hide: false,
      toggleView: true,
      liked: false,
      randomIndex: "",
      info: "循环播放",
    };
  },
  computed: {
    ...mapGetters([
      "id",
      "url",
      "isPlay",
      "playButtonUrl",
      "picUrl",
      "title",
      "artist",
      "duration",
      "curTime",
      "changeTime",
      "showAside",
      "listIndex",
      "listOfSongs",
      "autoNext",
      "loginIn",
      "userId",
      "control",
      "flag",
    ]),
  },
  mounted() {
    this.$store.commit("setPlayButtonUrl", "#icon-bofang");
    if (this.picUrl === "" || this.picUrl === null) {
      this.$store.commit(
        "setPicUrl",
        "https://www.freemusic.ltd/avatar/1.jpeg"
      );
    }
    this.progressLength = this.$refs.progress.getBoundingClientRect().width;
    this.$store.commit("setControl", "#icon-bofang-xunhuanbofang");
    this.$store.commit("setFlag", 0);
    this.$store.commit("setRecentSongList", []);
  },
  watch: {
    id() {
      this.getSongCollectState();
    },
    loginIn(val) {
      if (!val) {
        this.liked = false;
        this.$refs.like.children[0].style.color = "#ccc";
      } else {
        this.getSongCollectState();
      }
    },
    autoNext() {
      this.next();
    },
    curTime(val) {
      this.nowTime = this.formatTime(val);
      this.songTime = this.formatTime(this.duration);
      this.curLength = (val / this.duration) * 100;
    },
    volume(val) {
      this.$store.commit("setVolume", val / 100);
    },
  },
  methods: {
    changePlayState() {
      if (this.url === "" || this.url === null) {
        this.$message.warning("暂无歌曲");
      } else {
        if (this.control === "#icon-danquxunhuan" && this.flag === 2) {
          this.$store.commit("setControl", "#icon-bofang-xunhuanbofang");
        }
        if (this.control === "#icon-suijibofang" && this.flag === 1) {
          this.$store.commit("setControl", "#icon-danquxunhuan");
        }
        if (this.control === "#icon-bofang-xunhuanbofang" && this.flag === 0) {
          this.$store.commit("setControl", "#icon-suijibofang");
        }
        if (this.control === "#icon-suijibofang") {
          this.$store.commit("setFlag", 1);
          this.info = "随机播放";
        }
        if (this.control === "#icon-danquxunhuan") {
          this.$store.commit("setFlag", 2);
          this.info = "单曲循环";
        }
        if (this.control === "#icon-bofang-xunhuanbofang") {
          this.$store.commit("setFlag", 0);
          this.info = "循环播放";
        }
        this.$refs.info.classList.add("show-info");
      }
    },
    changeActive() {
      this.$refs.info.classList.add("show-info");
    },
    removeActive() {
      this.$refs.info.classList.remove("show-info");
    },
    changeAside() {
      this.$store.commit("setShowAside", !this.showAside);
    },
    showVolume() {
      this.hide = !this.hide;
    },
    togglePlay() {
      if (this.url !== "" && this.url !== null) {
        if (this.isPlay) {
          this.$store.commit("setIsPlay", false);
        } else {
          this.$store.commit("setIsPlay", true);
        }
      } else {
        this.$message.warning("暂无歌曲");
      }
    },
    formatTime(value) {
      let theTime = parseInt(value);
      let result = "";
      let hour = parseInt(theTime / 3600);
      let minute = parseInt((theTime / 60) % 60);
      let second = parseInt(theTime % 60);
      if (hour > 0) {
        if (hour < 10) {
          result = "0" + hour + ":";
        } else {
          result = hour + ":";
        }
      }
      if (minute > 0) {
        if (minute < 10) {
          result += "0" + minute + ":";
        } else {
          result += minute + ":";
        }
      } else {
        result += "00:";
      }
      if (second > 0) {
        if (second < 10) {
          result += "0" + second;
        } else {
          result += second;
        }
      } else {
        result += "00";
      }
      return result;
    },
    mousedown(e) {
      this.mouseStartX = e.clientX;
      this.tag = true;
    },
    mouseup() {
      this.tag = false;
    },
    mousemove(e) {
      if (this.url !== "" && this.url !== null) {
        if (!this.duration) {
          return false;
        }
        if (this.tag) {
          let movementX = e.clientX - this.mouseStartX;
          let curLength = this.$refs.curProgress.getBoundingClientRect().width;
          if (this.progressLength !== 0) {
            let newPercent =
              ((movementX + curLength) / this.progressLength) * 100;
            if (newPercent > 100) {
              newPercent = 100;
            }
            this.curLength = newPercent;
            this.mouseStartX = e.clientX;
            this.ChangeTime(newPercent);
          }
        }
      }
    },
    ChangeTime(percent) {
      let newCurTime = percent * 0.01 * this.duration;
      this.$store.commit("setChangeTime", newCurTime);
    },
    updatemove(e) {
      this.$refs.bg.style.cursor = "pointer";
      if (this.url !== "" && this.url !== null) {
        if (!this.tag) {
          let curLength = this.$refs.bg.offsetLeft;
          if (this.progressLength !== 0) {
            let newPercent =
              ((e.clientX - curLength) / this.progressLength) * 100;
            if (newPercent > 100) {
              newPercent = 100;
            } else if (newPercent < 0) {
              newPercent = 0;
            }
            this.curLength = newPercent;
            this.ChangeTime(newPercent);
          }
        }
      } else {
        this.$message.warning("暂无歌曲");
      }
    },
    prev() {
      if (this.listIndex !== -1 && this.listOfSongs.length > 1) {
        if (this.flag !== 0) {
          if (this.flag === 1) {
            this.getRandomIndex();
            this.$store.commit("setListIndex", this.randomIndex);
          }
          if (this.flag === 2) {
            if (this.curTime === 0) {
              this.$store.commit("setListIndex", this.listIndex);
              this.$store.commit("setCurTime", 0);
              this.$store.commit("setChangeTime", 0);
            } else {
              if (this.listIndex > 0) {
                this.$store.commit("setListIndex", this.listIndex - 1);
              } else {
                this.$store.commit("setListIndex", this.listOfSongs.length - 1);
              }
            }
          }
        } else {
          if (this.listIndex > 0) {
            this.$store.commit("setListIndex", this.listIndex - 1);
          } else {
            this.$store.commit("setListIndex", this.listOfSongs.length - 1);
          }
        }
        this.$store.commit("setCurTime", 0);
        this.$store.commit("setChangeTime", 0);
        if (this.isPlay) {
          this.start(this.listOfSongs[this.listIndex].url);
        } else {
          this.$store.commit("setIsPlay", true);
          this.start(this.listOfSongs[this.listIndex].url);
        }
        this.addPlayCount(this.id);
        if (localStorage.getItem("consumerName")) {
          let RecentSong = {
            userId: this.userId,
            songId: this.id,
            count: 1,
          };
          this.addRecentSong(RecentSong);
        }
      } else {
        this.$store.commit("setIsPlay", false);
        this.$store.commit("setPlayButtonUrl", "#icon-bofang");
        this.$message.warning("歌曲列表只有一首歌曲或没有歌曲");
      }
    },
    next() {
      if (this.listIndex !== -1 && this.listOfSongs.length > 1) {
        if (this.flag !== 0) {
          if (this.flag === 1) {
            this.getRandomIndex();
            this.$store.commit("setListIndex", this.randomIndex);
          }
          if (this.flag === 2) {
            if (this.curTime === 0) {
              this.$store.commit("setListIndex", this.listIndex);
              this.$store.commit("setCurTime", 0);
              this.$store.commit("setChangeTime", 0);
            } else {
              if (this.listIndex < this.listOfSongs.length - 1) {
                this.$store.commit("setListIndex", this.listIndex + 1);
              } else {
                this.$store.commit("setListIndex", 0);
              }
            }
          }
        } else {
          if (this.listIndex < this.listOfSongs.length - 1) {
            this.$store.commit("setListIndex", this.listIndex + 1);
          } else {
            this.$store.commit("setListIndex", 0);
          }
        }
        this.$store.commit("setCurTime", 0);
        this.$store.commit("setChangeTime", 0);
        if (this.isPlay) {
          this.start(this.listOfSongs[this.listIndex].url);
        } else {
          this.$store.commit("setIsPlay", true);
          this.start(this.listOfSongs[this.listIndex].url);
        }
        this.addPlayCount(this.id);
        if (localStorage.getItem("consumerName")) {
          let RecentSong = {
            userId: this.userId,
            songId: this.id,
            count: 1,
          };
          this.addRecentSong(RecentSong);
        }
      } else {
        this.$store.commit("setIsPlay", false);
        this.$store.commit("setPlayButtonUrl", "#icon-bofang");
        this.$message.warning("歌曲列表只有一首歌曲或没有歌曲");
      }
    },
    getRandomIndex() {
      this.randomIndex = parseInt(Math.random() * this.listOfSongs.length, 10);
    },
    start(url) {
      if (url && url !== this.url) {
        this.$store.commit("setId", this.listOfSongs[this.listIndex].id);
        this.$store.commit("setUrl", url);
        this.$store.commit("setPicUrl", this.listOfSongs[this.listIndex].pic);
        this.$store.commit("setTitle", this.listOfSongs[this.listIndex].name);
        this.$store.commit(
          "setArtist",
          this.listOfSongs[this.listIndex].singerName
        );
        this.$store.commit(
          "setLyric",
          this.parseLyric(this.listOfSongs[this.listIndex].lyric)
        );
      }
    },
    toLyric() {
      if (this.url !== "" && this.url !== null) {
        this.$router.push({ path: "/lyric" });
      } else {
        this.$message.warning("暂无歌曲");
      }
    },
    downloadMP3() {
      if (this.url !== "" && this.url !== null) {
        if (localStorage.getItem("consumerName")) {
          axios({
            method: "GET",
            url: this.url,
            responseType: "blob",
          }).then((res) => {
            let content = res.data;
            let eleLink = document.createElement("a");
            eleLink.download = `${this.artist}-${this.title}.mp3`;
            let blob = new Blob([content]);
            eleLink.href = URL.createObjectURL(blob);
            document.body.appendChild(eleLink);
            eleLink.click();
            document.body.removeChild(eleLink);
            setTimeout(() => {
              this.$message.success("下载成功");
            }, 1000);
          });
        } else {
          this.$message.warning("未登录不能下载");
        }
      } else {
        this.$message.warning("暂无歌曲");
      }
    },
    getSongCollectState() {
      let CollectVo = {
        userId: this.userId,
        type: 0,
        songId: this.id,
        songListId: "",
      };
      if (localStorage.getItem("consumerName")) {
        if (this.id !== "" && this.id !== null) {
          getCollectBySongId(CollectVo).then((res) => {
            if (res.data.code === 200) {
              this.liked = true;
              this.$refs.like.children[0].style.color = "#DC143C";
            } else {
              this.liked = false;
              this.$refs.like.children[0].style.color = "#ccc";
            }
          });
        }
      }
    },
    collect() {
      let Collect = {
        userId: this.userId,
        type: 0,
        songId: this.id,
        songListId: "",
      };
      if (localStorage.getItem("consumerName")) {
        if (this.id !== "" && this.id !== null) {
          if (!this.liked) {
            addCollect(Collect).then((res) => {
              if (res.data.code === 200) {
                this.liked = true;
                this.$refs.like.children[0].style.color = "#DC143C";
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
                this.$refs.like.children[0].style.color = "#ccc";
                this.getUserCollectData();
                this.$message.success(res.data.msg);
              } else {
                this.getUserCollectData();
              }
            });
          }
        } else {
          this.$message.warning("暂无歌曲");
        }
      } else {
        this.$message.warning("未登录, 不能收藏");
      }
    },
    getUserCollectData() {
      getAllCollectByConsumerId(this.userId).then((res) => {
        if (res.data.code === 200) {
          this.$store.commit("setCollectList", res.data.data);
        } else {
          this.$store.commit("setCollectList", []);
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/play-bar";
</style>
