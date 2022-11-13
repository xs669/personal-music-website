<template>
  <div class="box">
    <div class="the-aside">
      <div class="img">
        <img style="width: 100%" :src="picUrl" alt="图片" />
      </div>
      <div class="show">
        <h3>
          {{ "歌手: " }}<span>{{ artist }}</span>
        </h3>
        <h3 style="margin-top: 20px">
          {{ "所属专辑: " }}<span>{{ introduction }}</span>
        </h3>
      </div>
    </div>
    <div class="content">
      <div class="lyric-title">
        <h1>{{ title }}</h1>
        <h3 style="font-weight: normal; color: #fff">
          {{ artist + "-" + title }}
        </h3>
      </div>
      <div class="song-lyric" ref="lyr">
        <ul class="has-lyric" v-if="lyr.length">
          <li v-for="(item, index) in lyr" :key="index">
            {{ item[1] }}
          </li>
        </ul>
        <ul class="no-lyric" v-if="lyr.length === 0">
          <li>暂无歌词</li>
        </ul>
      </div>
      <div class="comment">
        <song-comment :play-id="id" :type="0"></song-comment>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixins } from "@/mixins";
import Comment from "../components/Comment";
import SongComment from "../components/SongComment";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Lyric",
  // eslint-disable-next-line vue/no-unused-components
  components: { SongComment, Comment },
  mixins: [mixins],
  data() {
    return {
      lyr: [],
    };
  },
  computed: {
    ...mapGetters([
      "id",
      "url",
      "curTime",
      "lyric",
      "listIndex",
      "listOfSongs",
      "title",
      "artist",
      "picUrl",
      "introduction",
    ]),
  },
  created() {
    this.lyr = this.lyric;
    this.$store.commit("setCurTime", 0);
  },
  watch: {
    url() {
      this.$store.commit("setCurTime", 0);
      this.lyr = this.parseLyric(this.listOfSongs[this.listIndex].lyric);
    },
    curTime(val) {
      if (val === 0) {
        this.$refs.lyr.scrollTop = 0;
      }
      if (this.lyr.length > 0) {
        for (let i = 0; i < this.lyr.length; i++) {
          if (val >= this.lyr[i][0]) {
            let top = this.$refs.lyr.children[0].children[i].offsetTop;
            let halfViewHeight = 200;
            let lyricHeight = 40;
            for (let j = 0; j < this.lyr.length; j++) {
              document.querySelectorAll(".has-lyric li")[j].style.color =
                "#fff";
              document.querySelectorAll(".has-lyric li")[j].style.fontSize =
                "16px";
              document.querySelectorAll(".has-lyric li")[j].style.fontWeight =
                "normal";
            }
            if (i >= 0) {
              document.querySelectorAll(".has-lyric li")[i].style.color =
                "#31c27c";
              document.querySelectorAll(".has-lyric li")[i].style.fontSize =
                "20px";
              document.querySelectorAll(".has-lyric li")[i].style.fontWeight =
                "bold";
              if (top > halfViewHeight + lyricHeight) {
                this.$refs.lyr.scrollTop = top - halfViewHeight;
              }
            }
          }
        }
      }
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/lyric";
</style>
