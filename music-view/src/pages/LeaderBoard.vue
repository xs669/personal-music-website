<template>
  <div class="leader-board-album">
    <div class="album-slide">
      <div class="leader-board-img">
        <img src="http://localhost/109951166952713766.jpg" alt="图片" />
      </div>
    </div>
    <div class="album-content">
      <div class="intro">
        <h2>播放量排名前50的歌曲</h2>
      </div>
      <album-content :song-lists="listOfSongs">
        <template slot="title">歌曲排行榜</template>
      </album-content>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixins } from "@/mixins";
import AlbumContent from "../components/AlbumContent";

export default {
  name: "LeaderBoard",
  components: { AlbumContent },
  mixins: [mixins],
  computed: {
    ...mapGetters(["id", "listOfSongs"]),
  },
  created() {
    this.getSongByPlayCount();
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/leader-board";
</style>
