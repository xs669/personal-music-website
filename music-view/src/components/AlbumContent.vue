<template>
  <div class="content">
    <h1 class="title">
      <slot name="title"></slot>
      <hr />
    </h1>
    <ul>
      <li>
        <div class="song-item">
          <span class="item-index"></span>
          <span class="item-title">歌曲名</span>
          <span class="item-name">歌手</span>
          <span class="item-intro">所属专辑</span>
          <span class="item-count">播放量</span>
        </div>
      </li>
      <li v-for="(item, index) in songLists" :key="index">
        <div
          id="changeColor"
          class="song-item"
          :class="{ 'is-play': item.id === id }"
          @click="
            toPlay(
              item.id,
              item.url,
              item.pic,
              index,
              item.name,
              item.singerName,
              item.lyric,
              item.introduction
            )
          "
        >
          <span class="item-index">{{ index + 1 }}</span>
          <span class="item-title">{{ item.name }}</span>
          <span class="item-name">{{ item.singerName }}</span>
          <span class="item-intro">{{ item.introduction }}</span>
          <span class="item-count">{{ item.playCount }}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { mixins } from "@/mixins";

export default {
  name: "AlbumContent",
  props: ["songLists"],
  mixins: [mixins],
  computed: {
    ...mapGetters(["listOfSongs", "id"]),
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/album-content";
</style>
