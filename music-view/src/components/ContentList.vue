<template>
  <div class="content-list">
    <ul class="section-content">
      <li
        class="content-item"
        v-for="(item, index) in contentLists"
        :key="index"
      >
        <div class="kuo" @click="goAlbum(item, item.name)">
          <img class="item-img" :src="item.pic" alt="头像" />
          <div class="mask">
            <svg class="icon">
              <use xlink:href="#icon-bofang"></use>
            </svg>
          </div>
        </div>
        <p v-if="item.name" class="item-name">{{ item.name }}</p>
        <p v-if="item.title" class="item-name">{{ item.title }}</p>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "contentList",
  props: ["contentLists"],
  methods: {
    goAlbum(item, type) {
      this.$store.commit("setTempList", item);
      if (type) {
        this.$router.push({ path: "/singerAlbum", query: { id: item.id } });
      } else {
        this.$router.push({ path: "/songListAlbum", query: { id: item.id } });
      }
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/content-list";
</style>
