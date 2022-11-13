<template>
  <div>
    <the-header></the-header>
    <the-aside></the-aside>
    <div class="content-box" :class="{ 'content-collapse': collapse }">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import TheHeader from "./TheHeader";
import TheAside from "./TheAside";
import bus from "../assets/js/bus";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Home",
  components: {
    TheHeader,
    TheAside,
  },
  data() {
    return {
      collapse: false,
    };
  },
  created() {
    bus.$on("collapse", (msg) => {
      this.collapse = msg;
    });
  },
};
</script>

<style scoped></style>
