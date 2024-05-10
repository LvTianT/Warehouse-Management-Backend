<template>
  <div>
    <div style="margin-top: 10px; ">
      <form class="form" style="display: inline-block;margin-right: 676px;margin-left:10px;transform: translateY(3px);">
        <label for="search">
          <input
            required=""
            autocomplete="off"
            placeholder="请输入盘库者姓名"
            id="search"
            type="text"
            v-model="checker"
            style="width: 250px;"
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
     <div style="float: right;transform: translateY(-60px);"> <div class="block" style="margin-top: 30px;margin-left:15px;display: inline-block;">
      <span
        class="demonstration"
        style="font-size: 14px; margin-right: 20px;"
        >盘库时间:</span
      >
      <el-date-picker
        size="small"
        v-model="value1"
        type="datetime"
        placeholder="请选择"
        align="right"
        :picker-options="pickerOptions"
        style="display: inline-block;"
        @change="DateTime"
      >
      </el-date-picker>
      </div>
      <el-button
          icon="el-icon-plus"
          class="button2 button3"
          size="medium"
          type="primary"
          style="
            margin-left: 20px;
            display: inline-block;
            margin-right: 10px;
          "
          @click="add"
          round
          >新增</el-button
        >
        <el-button
          icon="el-icon-refresh-right"
          class="button2 button4"
          size="medium"
          @click="resetParam"
          round
          style="margin-right: 10px"
          >重置</el-button
        >
    </div>
    </div>

    <el-table
      style="transform: translateY(-30px);
        width: 100%;
        border-radius: 20px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      "
      :data="tableData"
      :header-cell-style="{ background: '#fff', color: '#555555' }"
      border
    >
        <el-table-column prop="inventorycheckid" label="记录序号" width="150"> </el-table-column>
      <el-table-column prop="productid" label="产品编号" width="150"> </el-table-column>
      <el-table-column prop="checkedquantity" label="当天库存" width="150"> </el-table-column>
      <el-table-column prop="checkdate" label="盘库时间" width="250"> </el-table-column>
      <el-table-column prop="checker" label="盘库人员" width="150"></el-table-column>
      <el-table-column prop="operate" label="操作" class="cell">
        <template slot-scope="scope">
          <el-popconfirm
            title="确定删除此记录吗？"
            @confirm="del(scope.row.loanid)"
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
    
     <el-pagination
      style="margin-top: 10px; margin-left: 15px;margin-bottom:120px;"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 20, 30]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
    <el-dialog
      title="新增"
      :visible.sync="dialogVisible"
      width="33%"
      center
    >
       <el-dialog
        width="75%"
        title="用户选择"
        :visible.sync="innerVisible"
        append-to-body
      >
        <Usertable @doSelectUser="doSelectUser"></Usertable>
        <span slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false" >取 消</el-button>
          <el-button type="primary" @click="confirmUser">确 定</el-button>
        </span>
      </el-dialog>
      <el-form
        ref="form"
        :model="form"
        label-width="80px"
        :rules="rules"
        style="transform: translateX(25px)"
      >
        <el-form-item label="姓名" prop="checker">
            <el-col :span="18">
            <el-input
              v-model="form.checker"
              size="small"
              readonly
              @click.native="selectUser"
            ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="产品编号" prop="productid">
          <el-col :span="18"
            ><el-input v-model="form.productid" size="small"></el-input
          ></el-col>
        </el-form-item>
                <el-form-item label="库存" prop="checkedquantity">
          <el-col :span="18">
            <el-input v-model="form.checkedquantity" size="small"></el-input>
            </el-col>
              </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          @click="dialogVisible = false"
          style="transform: translate(15px,-25px); margin-right: 10px;"
          size="medium"
          >取 消</el-button
        >
        <el-button
          type="primary"
          @click="save"
          style="transform:  translate(15px,-25px);"
          size="medium"

          >提 交</el-button
        >
      </span>
    </el-dialog>
    
  </div>
</template>

<script>
import Usertable from "../user/Usertable.vue";
export default {
  name: "Inventorycheck",
    components: { Usertable },
  data() {
    return {
      tempUser: {},
      tableData: [],
      UserData:[],
      checker:"",
      timecheckTable:[],
      pageSize: 10,
      pageNum: 1,
      total: 0,
       borrower: "",
      dialogVisible: false,
      dialogVisible2: false,
          innerVisible: false,
      form:{
        productid:"",
        checkedquantity:"",
        checker:"",
      },
      rules:{
         borrower: [{ required: true, message: "请输入姓名", trigger: "blur" }],
      },
        pickerOptions: {
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },
      value1: "",
      value2: "",
    };
  },
  methods: {
     confirmUser() {
      this.form.checker = this.tempUser.name;
      this.innerVisible = false;
    },
    selectUser() {
      this.innerVisible = true;
    },
    doSelectUser(val) {
      console.log(val);
      this.tempUser = val;
    },
     DateTime(time) {
      console.log(time);
      const dt = new Date(time);
      const newHour = dt.getHours() + 8;
      dt.setHours(newHour);
      const borrowtime = dt.toJSON();
     const datetime=borrowtime.slice(0,10);
      this.$axios
        .post(this.$httpUrl + "/inventorycheck/listdatetime?datetime=" + datetime)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code === 200) {
            this.tableData = res.data;
            this.total = res.total;
          } else {
            alert("暂无盘库信息");
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

    resetForm() {
      this.$refs.form.resetFields();
    },
    del(id) {
      console.log(id);

      this.$axios
        .get(this.$httpUrl + "/inventorycheck/del?id=" + id)
        .then((res) => {
          console.log(res);
          if (res.status=== 200) {
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
           this.form.cheker = row.checker;
           this.form.checkedquantity = row.checkedquantity;
           this.form.productid = row.productid;
           this.form.inventorycheckid=row.inventorycheckid;
      });
    },
    doSave() {
      this.$axios
        .post(this.$httpUrl + "/inventorycheck/save",this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code === 200) {
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

    add() {
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
      });
    },
    doMod() {
      this.$axios
        .post(this.$httpUrl + "/inventorycheck/update", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code === 200) {
            this.$message({
              message: "修改成功！",
              type: "success",
            });

            this.loadPost();
            this.resetForm();
            this.dialogVisible2 = false;
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
            this.doSave();
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    resetParam() {
      this.checker = "";
    },
     loadUser(){
      this.$axios
        .get(this.$httpUrl + "/user/list")
        .then((res) => {
          if (res.status === 200) {
            this.UserData = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadPost() {
      this.$axios
        .post(this.$httpUrl + "/inventorycheck/Checklist",{
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            checker:this.checker
          },
        })
        .then((res) => {
          if (res.status=== 200) {
            console.log(res);
            this.tableData = res.data.data;
            this.total = res.data.total;
          } else {
            alert("获取数据失败");
          }
        });
    },
  },
  beforeMount() {
    this.loadPost();
     this.loadUser()
  },
};
</script>

<style scoped>
.input {
  margin: 0 auto;
}
.el-table {
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

.button2 {
  display: inline-block;
  transition: all 0.2s ease-in;
  position: relative;
  overflow: hidden;
  z-index: 1;
  color: #090909;
  padding: 0.7em 1.2em;
  cursor: pointer;
  font-size: 13px;
  border-radius: 0.5em;
  background: #d7f2df;
  border: 1px solid #fffefe;
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
  background-color: #bcdafe;
}

.button4 {
  background-color: #cbf9e4;
}
</style>
