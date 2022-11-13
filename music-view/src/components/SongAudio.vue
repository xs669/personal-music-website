<template>
  <div class="song-audio">
    <audio
      ref="player"
      preload="auto"
      :src="url"
      controls="controls"
      @canplay="startPlay"
      @ended="ended"
      @timeupdate="timeUpdate"
      muted="muted"
      style="display: none"
    ></audio>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  name: "song-audio",
  computed: {
    ...mapGetters([
      "id",
      "url",
      "isPlay",
      "listOfSongs",
      "curTime",
      "changeTime",
      "autoNext",
      "volume",
      "flag",
    ]),
  },
  mounted() {
    if (window.name === "isRefresh") {
      this.$store.commit("setIsPlay", false);
      this.$store.commit("setPlayButtonUrl", "#icon-bofang");
    }
  },
  watch: {
    isPlay(val) {
      if (val) {
        this.startPlay();
      } else {
        this.togglePlay();
      }
    },
    changeTime(val) {
      if (val !== 0) {
        this.$refs.player.currentTime = this.changeTime;
      } else {
        this.$refs.player.currentTime = this.changeTime;
      }
    },
    volume(val) {
      this.$refs.player.volume = val;
    },
  },
  methods: {
    // 获取链接后准备播放
    startPlay() {
      let player = this.$refs.player;
      this.$store.commit("setDuration", player.duration);
      this.$store.commit("setPlayButtonUrl", "#icon-zanting");
      if (this.isPlay) {
        // 开始播放
        setTimeout(() => {
          player.play();
        }, 200);
      } else {
        this.$store.commit("setPlayButtonUrl", "#icon-bofang");
        player.pause();
      }
    },
    // 播放完成之后触发
    ended() {
      this.$store.commit("setCurTime", 0);
      this.$store.commit("setAutoNext", !this.autoNext);
    },
    // 暂停
    togglePlay() {
      let player = this.$refs.player;
      this.$store.commit("setPlayButtonUrl", "#icon-bofang");
      player.pause();
    },
    timeUpdate() {
      this.$store.commit("setCurTime", this.$refs.player.currentTime);
    },
  },
};
</script>

<style scoped lang="scss"></style>
