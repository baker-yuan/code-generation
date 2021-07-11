<template>
  <div class="app-container">
    <div class="head-container">
      <!-- 搜索表单 -->
      <el-form
          :inline="false"
          :model="dataForm"
          @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-input v-model="dataForm.name"
                    clearable
                    size="small"
                    placeholder="请输入表名"
                    style="width: 200px;"
                    @keyup.enter.native="getDataList"/>
          <el-button type="success" style="margin-left: 8px" size="small" @click="getDataList()">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-tooltip class="item" effect="dark" content="数据库中表字段变动时使用该功能" placement="top-start">
            <el-button
                size="mini"
                type="success"
                icon="el-icon-refresh"
                :loading="syncLoading"
                :disabled="dataListSelections.length === 0"
                @click="sync">同步
            </el-button>
          </el-tooltip>
        </el-form-item>
      </el-form>
    </div>
    <!-- 表格 -->
    <el-table ref="table"
              :data="dataList"
              style="width: 100%;"
              @selection-change="selectionChangeHandle">
      <el-table-column type="selection" width="55"/>
      <el-table-column :show-overflow-tooltip="true" prop="tableName" label="表名"/>
      <el-table-column :show-overflow-tooltip="true" prop="engine" label="数据库引擎"/>
      <el-table-column :show-overflow-tooltip="true" prop="coding" label="字符编码集"/>
      <el-table-column :show-overflow-tooltip="true" prop="remark" label="备注"/>
      <el-table-column prop="createTime" label="创建日期"/>
      <el-table-column label="操作" width="160px" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" style="margin-right: 2px" type="text">
            <router-link :to="{ name: 'PREVIEW', params: { tableName: scope.row.tableName }}">
              预览
            </router-link>
          </el-button>
          <el-button size="mini" style="margin-left: -1px;margin-right: 2px" type="text"
                     @click="toDownload(scope.row.tableName)">下载
          </el-button>
          <el-button size="mini" style="margin-left: -1px;margin-right: 2px" type="text">
            <router-link :to="{ name: 'CONFIG', params: { tableName: scope.row.tableName }}">
              配置
            </router-link>
          </el-button>
          <el-button type="text" style="margin-left: -1px" size="mini" @click="toGen(scope.row.tableName)">生成
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
        @size-change="sizeChangeHandle"
        @current-change="currentChangeHandle"
        :current-page="pageIndex"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :total="totalPage"
        layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>
import {downloadFile} from '@/utils/index'

export default {
  name: 'GeneratorIndex',
  data() {
    return {
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListSelections: [],
      dataForm: {
        name: ''
      },
      syncLoading: false
    }
  },
  created() {
    this.getDataList()
  },
  methods: {
    // 查询数据库元数据
    getDataList() {
      this.$http({
        url: this.$http.adornUrl('api/generator/tables'),
        method: 'get',
        params: this.$http.adornParams({
          'name': this.dataForm.name,
          'page': this.pageIndex,
          'size': this.pageSize,
        })
      }).then(({data}) => {
        this.dataList = data.data.rows
        this.totalPage = data.data.total
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val
    },
    /**
     * 生成代码
     *
     * @param tableName
     */
    toGen(tableName) {
      // this.$http({
      //   url: this.$http.adornUrl('api/generator/' + tableName + '/' + 0),
      //   method: 'post',
      //   data: this.$http.adornData({}),
      // }).then(({data}) => {
      //   if (data.status === 0) {
      //     this.$message({
      //       showClose: true,
      //       message: '生成成功',
      //       center: true,
      //       type: 'success',
      //       duration: 2500
      //     })
      //   }
      // })
    },
    /**
     * 打包下载
     *
     * @param tableName
     */
    toDownload(tableName) {
      this.$http({
        url: this.$http.adornUrl('api/generator/' + tableName + '/' + 2),
        method: 'post',
        data: this.$http.adornData({}),
        responseType: 'blob'
      }).then(({data}) => {
        downloadFile(data, tableName, 'zip')
      })
    },

    /**
     *
     */
    sync() {
      const tables = []
      this.dataListSelections.forEach(val => {
        tables.push(val.tableName)
      })
      console.log(tables);
      this.syncLoading = true
      this.$http({
        url: this.$http.adornUrl('api/generator/sync'),
        method: 'post',
        data: this.$http.adornData({
          "tables": tables
        }),
        responseType: 'blob'
      }).then(({data}) => {
        if (data.status === 0) {
          this.$message({
            showClose: true,
            message: '同步成功',
            center: true,
            type: 'success',
            duration: 2500
          })
        }
        this.syncLoading = false
      }).catch(() => {
        this.syncLoading = false
      })


    }
  }
}
</script>

<style lang="scss" scoped>

</style>
