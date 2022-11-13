<template>
  <transition name="slide-fade">
    <div class="the-aside" v-if="showAside">
      <h2 class="title">播放列表</h2>
      <ul class="menus">
        <li
          v-for="(item, index) in listOfSongs"
          :key="index"
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
          {{ item.name }}
        </li>
      </ul>
    </div>
  </transition>
</template>

<script>
import { mapGetters } from "vuex";
import { mixins } from "@/mixins";

export default {
  name: "TheAside",
  mixins: [mixins],
  computed: {
    ...mapGetters(["showAside", "listOfSongs", "id"]),
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/the-aside";
</style>
