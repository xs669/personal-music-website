<template>
  <div class="search-song-Lists">
    <content-list :content-lists="searchSongListData"></content-list>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import ContentList from "../ContentList";

export default {
  name: "SearchSongLists",
  components: { ContentList },
  computed: {
    ...mapGetters(["searchSongListData"]),
  },
};
</script>

<style scoped lang="scss">
@import "../../assets/css/search-song-Lists";
</style>
