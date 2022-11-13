<template>
  <div class="scroll-top" @click="returnTop">
    <div class="box-in"></div>
  </div>
</template>

<script>
export default {
  name: "ScrollTop",
  methods: {
    returnTop() {
      document.documentElement.scrollTop = document.body.scrollTop = 0;
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/scroll-top.scss";
</style>
