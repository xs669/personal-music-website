<template>
  <div class="song-audio">
    <audio
      id="player"
      :src="url"
      preload="auto"
      controls="controls"
      @canplay="startPlay"
      @ended="ended"
    ></audio>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  name: "song-audio",
  data() {
    return {
      flag: 0,
    };
  },
  computed: {
    ...mapGetters(["id", "url", "isPlay"]),
  },
  watch: {
    isPlay(val) {
      let _this = this;
      if (val) {
        if (_this.flag < 1) {
          setTimeout(function () {
            _this.startPlay();
            _this.flag += 1;
          }, 500);
        } else {
          _this.startPlay();
        }
      } else {
        this.togglePlay();
      }
    },
  },
  methods: {
    // 获取链接后准备播放
    startPlay() {
      let player = document.querySelector("#player");
      // 开始播放
      player.play();
    },
    // 播放完成之后触发
    ended() {
      this.$store.commit("setIsPlay", false);
      this.flag = 0;
    },
    // 暂停
    togglePlay() {
      let player = document.querySelector("#player");
      player.pause();
    },
  },
};
</script>

<style>
.song-audio {
  display: none;
}
</style>
