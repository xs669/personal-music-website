<template>
  <div class="setting">
    <div class="leftCol">
      <div class="settingsMainHeader">设置</div>
      <ul class="setting-aside">
        <li
          v-for="(item, index) in settingList"
          :key="index"
          :class="{ activeColor: activeName === item.name }"
        >
          {{ item.name }}
        </li>
      </ul>
    </div>
    <div class="contentCol">
      <info></info>
    </div>
  </div>
</template>

<script>
import Info from "../components/Info";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Setting",
  components: { Info },
  data() {
    return {
      settingList: [{ name: "个人信息", path: "Info" }],
      activeName: "个人信息",
    };
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/setting";
</style>
