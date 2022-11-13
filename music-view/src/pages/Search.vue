<template>
  <div class="search">
    <nav class="searchList-nav" ref="change">
      <span
        :class="{ isActive: toggle === 'Songs' }"
        @click="handleChangeView('Songs')"
        >歌曲</span
      >
      <span
        :class="{ isActive: toggle === 'SongLists' }"
        @click="handleChangeView('SongLists')"
        >歌单</span
      >
    </nav>
    <component :is="currentView"></component>
  </div>
</template>

<script>
import SearchSongLists from "../components/search/SearchSongLists";
import SearchSongs from "../components/search/SearchSongs";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Search",
  components: { SearchSongs, SearchSongLists },
  data() {
    return {
      toggle: "Songs",
      currentView: "SearchSongs",
    };
  },
  methods: {
    handleChangeView(component) {
      this.toggle = component;
      this.currentView = "Search" + component;
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/search";
</style>
