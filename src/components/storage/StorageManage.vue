<template>
  <div>
    <div
      style="margin-top: 5px; margin-left:-3px;display: inline-block"
      class="input"
    >
      <span style="margin-right: 15px; margin-left: 22px; font-size: 15px"
        >库名:</span
      >
      <form class="form" style="display: inline-block; margin-right: 60px">
        <label for="search">
          <input
            required=""
            autocomplete="off"
            placeholder="请输入"
            id="search"
            type="text"
            v-model="name"
            style="width: 250px; margin-right: 15px"
            @keyup="loadPost"
          />
          <div class="icon">
            <svg
              stroke-width="2"
              stroke="currentColor"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              class="swap-on"
            >
              <path
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                stroke-linejoin="round"
                stroke-linecap="round"
              ></path>
            </svg>
            <svg
              stroke-width="2"
              stroke="currentColor"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              class="swap-off"
            >
              <path
                d="M10 19l-7-7m0 0l7-7m-7 7h18"
                stroke-linejoin="round"
                stroke-linecap="round"
              ></path>
            </svg>
          </div>
          <button type="reset" class="close-btn">
            <svg
              viewBox="0 0 20 20"
              class="h-5 w-5"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                clip-rule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                fill-rule="evenodd"
              ></path>
            </svg>
          </button>
        </label>
      </form>
      <!-- <el-input
        v-model="name"
        size="small"
        placeholder="请输入"
        suffix-icon="el-icon-search"
        style="width: 250px; margin-top: 10px; display: inline-block"
        @keyup.enter.native="loadPost"
      ></el-input> -->
      <el-button
       icon="el-icon-search"
        size="medium"
        style="margin-top: 10px; display: inline-block; margin-left: -40px"
        @click="loadPost"
        round
        class="button2"
        type="primary"
        plain
        >查询</el-button
      >
      <el-button
          icon="el-icon-plus"
          size="medium"
          type="primary"
          class="button2 button3"
          style="margin-left: 580px; margin-bottom: 10px; margin-right:3px;display: inline-block;"
          @click="add"
          round
      >新增</el-button
      >

      <el-button icon="el-icon-refresh-right" size="medium" class="button2 button4" @click="resetParam" round
      >重置</el-button
      >
    </div>

<!--    <div style="margin-left: 600px; margin-bottom: 10px; display: inline-block">-->
<!--     -->
<!--    </div>-->

    <div style="position: relative; margin: 0 auto">
      <el-table
        style="
          clear: both;
          text-align: center;
          width:98%;
          justify-content: center;
          margin-left:15px;
          border-radius: 20px;
          box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
          margin-top: 15px;
        "
        :data="tableData"
        :header-cell-style="{ background: '#fff', color: '#555555' }"
        border
      >
        <el-table-column
          prop="warehouseId"
          label="仓库编号"
          width="150"
          style="text-align: center"
        >
        </el-table-column>
        <el-table-column prop="name" label="仓库名" width="150">
        </el-table-column>
        <el-table-column prop="remark" label="备注" width="450">
        </el-table-column>
        <el-table-column prop="operate" label="操作" class="cell">
          <template slot-scope="scope">
            <el-button
              class="edit"
              size="medium"
              icon="el-icon-edit"
              @click="mod(scope.row)"
              circle
              style="margin-right: 30px; margin-left: 15px"
            ></el-button>
            <el-popconfirm
              title="此为永久性操作，确定删除该仓库吗？"
              @confirm="del(scope.row.warehouseId)"
              type="text"
            >
              <el-button
                class="del"
                slot="reference"
                size="medium"
                type="primary"
                icon="el-icon-delete"
                circle
              ></el-button
            ></el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-pagination
      style="margin-top: 40px; transform: translate(290px, -20px)"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <el-dialog
      style="border-radius: 15px"
      title="新增仓库"
      :visible.sync="dialogVisible"
      width="33%"
      center
    >
      <el-form
        ref="form"
        :model="form"
        label-width="70px"
        style="transform: translateX(25px)"
      >
        <el-form-item label="仓库名" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="18">
            <el-input v-model="form.remark" size="small"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="dialogVisible = false"
          style="transform: translate(8px, -30px);margin-right: 15px;"
          size="medium"
          >取 消</el-button
        >
        <el-button
          type="primary"
          @click="save"
          style="transform: translateY(-30px)"
          size="medium"
          >提 交</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      style="border-radius: 15px"
      title="编辑仓库"
      :visible.sync="dialogVisible2"
      width="33%"
      center
    >
      <el-form
        ref="form"
        :model="form"
        label-width="70px"
        style="transform: translateX(25px)"
      >
        <el-form-item label="仓库名" prop="name">
          <el-col :span="18">
            <el-input v-model="form.name" size="small"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-col :span="18">
            <el-input v-model="form.remark" size="small"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="dialogVisible2 = false"
          style="transform: translate(12px, -30px); margin-right: 10px"
          size="medium"
          >取 消</el-button
        >
        <el-button
          type="primary"
          @click="save"
          style="transform: translate(13px, -30px)"
          size="medium"
          >保 存</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "StorageManage",
  data() {
    return {
      text: "",
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: "",
      dialogVisible: false,
      dialogVisible2: false,
      form: {
        warehouseId: "",
        name: "",
        remark: "",
      },
      rules: {
        name: [{ required: true, message: "请输入仓库名", trigger: "blur" }],
      },
    };
  },
  methods: {
    resetForm() {
      this.$refs.form.resetFields();
    },
    del(warehouseId) {
      console.log(warehouseId);

      this.$axios
        .get(this.$httpUrl + "/Warehouses/del?id=" + warehouseId)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "删除成功！",
              type: "success",
            });
            this.loadPost();
          } else {
            this.$message({
              message: "删除失败！",
              type: "error",
            });
          }
        });
    },
    mod(row) {
      console.log(row);
      this.dialogVisible2 = true;
      this.$nextTick(() => {
        this.form.warehouseId = row.warehouseId;
        this.form.name = row.name;
        this.form.remark = row.remark;
      });
    },
    doSave() {
      this.$axios
        .post(this.$httpUrl + "/Warehouses/save", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "添加成功！",
              type: "success",
            });
            this.dialogVisible = false;
            this.loadPost();
          } else {
            this.$message({
              message: "添加失败！",
              type: "error",
            });
          }
        });
    },
    init() {
      text: sessionStorage.getItem("text");
    },
    add() {
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
      });
    },
    doMod() {
      this.$axios
        .post(this.$httpUrl + "/Warehouses/update", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "修改成功！",
              type: "success",
            });
            this.dialogVisible2 = false;
            this.loadPost();
            this.resetForm();
          } else {
            this.$message({
              message: "修改失败！",
              type: "error",
            });
          }
        });
    },
    save() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (!this.form.id) {
            this.doMod();
          } else {
            this.doSave();
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum = 1;
      this.pageSize = val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.loadPost();
    },
    resetParam() {
      this.name = "";
    },
    loadPost() {
      this.$axios
        .post(this.$httpUrl + "/Warehouses/listPage", {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            warehouseId: this.warehouseId,
          },
        })
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.tableData = res.data;
            this.total = res.total;
          } else {
            alert("获取数据失败");
          }
        });
    },
  },
  beforeMount() {
    this.loadPost();
  },
};
</script>

<style scoped>
.input {
  margin: 0 auto;
}
el-table {
  box-sizing: border-box;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  word-break: break-all;
  line-height: 23px;
  padding-left: 20px;
  padding-right: 10px;
}

.cell {
  padding-left: 10px;
}
.edit {
  border-color: transparent;
  color: #409eff;
  background: 0 0;
  margin-left: 65px;
  padding-left: 3px;
  padding-right: 0;
}
.del {
  border-color: transparent;
  color: #fa5b5b;
  background: #ffffff 0;
}
.text {
  width: 100px;
  height: 100px;
  /*padding:5px; */
  border-radius: 50px;
  background: #fcfcfc;
  box-shadow: 20px 20px 14px #d1d1d1, -20px -20px 14px #ffffff;
}
.button2 {
  display: inline-block;
  transition: all 0.2s ease-in;
  position: relative;
  overflow: hidden;
  z-index: 1;
  color: #090909;
  padding: 0.6em 1.1em;
  cursor: pointer;
  font-size: 13.5px;
  border-radius: 0.5em;
  background: #baf6cb99;
  border: 1px solid #ffffff;
  box-shadow: 6px 6px 12px #c5c5c5, -6px -6px 12px #ffffff;
}

.button2:active {
  color: #666;
  box-shadow: inset 4px 4px 12px #c5c5c5, inset -4px -4px 12px #ffffff;
}

.button2:before {
  content: "";
  position: absolute;
  left: 50%;
  transform: translateX(-50%) scaleY(1) scaleX(1.25);
  top: 100%;
  width: 140%;
  height: 180%;
  background-color: rgba(0, 0, 0, 0.05);
  border-radius: 50%;
  display: block;
  transition: all 0.5s 0.1s cubic-bezier(0.55, 0, 0.1, 1);
  z-index: -1;
}

.button2:after {
  content: "";
  position: absolute;
  left: 55%;
  transform: translateX(-50%) scaleY(1) scaleX(1.45);
  top: 180%;
  width: 160%;
  height: 190%;
  background-color: #95f0ea;
  border-radius: 50%;
  display: block;
  transition: all 0.5s 0.1s cubic-bezier(0.55, 0, 0.1, 1);
  z-index: -1;
}

.button2:hover {
  color: #ffffff;
  border: 1px solid #e2f5f4;
}

.button2:hover:before {
  top: -35%;
  background-color: #abcfec;
  transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
}

.button2:hover:after {
  top: -45%;
  background-color: #85b59c;
  transform: translateX(-50%) scaleY(1.3) scaleX(0.8);
}

.button3 {
  background-color: #cbe2fe;
}

.button4 {
  background-color: #bbeafb;
}
.form {
  --input-bg: #fff;
  /*  background of input */
  --padding: 1.5em;
  --rotate: 80deg;
  /*  rotation degree of input*/
  --gap: 2em;
  /*  gap of items in input */
  --icon-change-color: #15a986;
  /*  when rotating changed icon color */
 --height: 32px;
  /*  height */
  width: 233px;
  padding-inline-end: 1em;
  /*  change this for padding in the end of input */
  background: var(--input-bg);
  position: relative;
  border-radius: 4px;
}

.form label {
  display: flex;
  align-items: center;
  width: 100%;
  height: var(--height);
}

.form input {
  width: 100%;
  padding-inline-start: calc(var(--padding) + var(--gap));
  outline: none;
  background: none;
  border: 0;
}
/* style for both icons -- search,close */
.form svg {
  /* display: block; */
  color: #111;
  transition: 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: absolute;
  height: 15px;
}
/* search icon */
.icon {
  position: absolute;
  left: var(--padding);
  transition: 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  justify-content: center;
  align-items: center;
}
/* arrow-icon*/
.swap-off {
  transform: rotate(-80deg);
  opacity: 0;
  visibility: hidden;
}
/* close button */
.close-btn {
  /* removing default bg of button */
  background: none;
  border: none;
  right: calc(var(--padding) - var(--gap));
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #111;
  padding: 0.1em;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  transition: 0.3s;
  opacity: 0;
  transform: scale(0);
  visibility: hidden;
}

.form input:focus ~ .icon {
  transform: rotate(var(--rotate)) scale(1.3);
}

.form input:focus ~ .icon .swap-off {
  opacity: 1;
  transform: rotate(-80deg);
  visibility: visible;
  color: var(--icon-change-color);
}

.form input:focus ~ .icon .swap-on {
  opacity: 0;
  visibility: visible;
}

.form input:valid ~ .icon {
  transform: scale(1.3) rotate(var(--rotate));
}

.form input:valid ~ .icon .swap-off {
  opacity: 1;
  visibility: visible;
  color: var(--icon-change-color);
}

.form input:valid ~ .icon .swap-on {
  opacity: 0;
  visibility: visible;
}

.form input:valid ~ .close-btn {
  opacity: 1;
  visibility: visible;
  transform: scale(1);
  transition: 0s;
}
</style>
