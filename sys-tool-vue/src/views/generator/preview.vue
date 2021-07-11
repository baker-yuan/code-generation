<template>
  <el-tabs v-model="activeName" type="card" @tab-click="handTabClick">
    <el-tab-pane v-for="item in data" :key="item.name" :lazy="true" :label="item.name" :name="item.name">
      <Java :value="item.content" :height="height" />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import Java from '@/components/JavaEdit/index'
export default {
  name: 'Preview',
  components: { Java },
  data() {
    return {
      data: null, height: '', activeName: 'Entity'
    }
  },
  created() {
    this.height = document.documentElement.clientHeight - 180 + 'px'
    const tableName = this.$route.params.tableName
    this.$http({
      url: this.$http.adornUrl('api/generator/' + tableName + '/' + 1),
      method: 'post',
      data: this.$http.adornData({})
    }).then(({data}) => {
      this.data = data.data
    }).catch(() => {
      this.$router.go(-1)
    })
  },
  methods : {
    handTabClick(tab, event) {
      console.log(tab)
      console.log(event)
    },
    setClipboardText(value) {
      const text = document.createElement('textarea');
      text.value = value;
      document.body.appendChild(text);
      text.select();
      document.execCommand('Copy');
      text.remove();
    }
  }
}
</script>
