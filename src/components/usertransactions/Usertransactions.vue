<template>
  <div>
     <div style="margin-bottom: -2px;display: inline-block;">
      <el-input
        v-model="productName"
        placeholder="请输入物品名"
        size="small"
        suffix-icon="el-icon-search"
        style="width: 200px; margin-top: 10px; margin-left: 10px"
        @keyup.enter.native="loadPost"
      ></el-input>
       <el-button icon="el-icon-search" style="margin-left: 20px" @click="loadPost" class="button2"
        >查询</el-button
      >
        <el-button
        icon="el-icon-refresh-right"
          type="success"
          size="small"
          style="margin-left: 15px"
          @click="resetParam"
          class="button2 button4"
          >重置</el-button
        >
    </div>
     <el-table
      style="
        margin-left: 10px;
        margin-top: 30px;
        width: 99%;
        border-radius: 15px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      "
      :data="tableData"
      :header-cell-style="{ background: '#fff', color: '#555555' }"
      border
    >
      <el-table-column prop="productid" label="商品编号" width="130">
      </el-table-column>
           <el-table-column prop="productid" label="商品名称" width="130"  :formatter="formatProductsName">
      </el-table-column>
      <el-table-column prop="inquantity" label="入库数量"  width="120"> </el-table-column>
      <el-table-column prop="intime" label="最后入库时间" width="180"> </el-table-column>
      <el-table-column prop="instaff" label="入库人" width="130" :formatter="formatInName"> </el-table-column>
      <el-table-column prop="outquantity" label="出库数量"> </el-table-column>
      <el-table-column prop="outtime" label="最后出库时间"  width="180"> </el-table-column>
      <el-table-column prop="outstaff" label="出库人" width="130"  :formatter="formatOutName"> </el-table-column>
      <el-table-column prop="remark" label="备注"> </el-table-column>
    </el-table>
    <div class="block" style="margin-top: 30px; float: left;margin-bottom:10px;">
      <span
        class="demonstration"
        style="font-size: 14px; margin-right: 10px; margin-left: 10px"
        >入库时间:</span
      >
      <el-date-picker
        size="small"
        v-model="value1"
        type="datetime"
        placeholder="请选择"
        align="right"
        :picker-options="pickerOptions"
        style="margin-bottom: 20px"
        @change="checkInTime"
      >
      </el-date-picker>
      <span
        class="demonstration"
        style="font-size: 14px; margin-right: 10px; margin-left: 10px;"
        >出库时间:</span
      >
      <el-date-picker
        size="small"
        v-model="value2"
        type="datetime"
        placeholder="请选择"
        align="right"
        :picker-options="pickerOptions"
        style="margin-bottom: 10px"
        @change="checkOutTime"
      >
      </el-date-picker>
    </div>
    <el-table
      style="
        margin-left: 10px;
        width: 99%;
        border-radius: 15px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      "
      :data="timecheckTable"
      :header-cell-style="{ background: '#fff', color: '#555555' }"
      border
    >
      <el-table-column prop="productid" label="商品编号" width="160">
      </el-table-column>
      <el-table-column prop="inquantity" label="入库数量"  width="130"> </el-table-column>
      <el-table-column prop="intime" label="最后入库时间" width="180"> </el-table-column>
      <el-table-column prop="instaff" label="入库人" :formatter="formatInName"> </el-table-column>
      <el-table-column prop="outquantity" label="出库数量"> </el-table-column>
      <el-table-column prop="outtime" label="最后出库时间"  width="180"> </el-table-column>
      <el-table-column prop="outstaff" label="出库人" :formatter="formatOutName"> </el-table-column>
      <el-table-column prop="remark" label="备注"> </el-table-column>
    </el-table>
    <el-pagination
      style="margin-left: 10px; margin-top: 25px"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
  </div>
</template>

<script>
export default {
  name: "Usertransactions",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("CurUser")),
      storageData: [],
      goodstypeData: [],
      productsData:[],
      UserData:[],
      tableData: [],
      timecheckTable: [],
      pageSize: 8,
      pageNum: 1,
      total: 0,
      productName: "",
      warehouse: "",
      productsType: "",
      centerDialogVisible: false,
      centerDialogVisible2: false,
      form: {
        productId: "",
        productName: "",
        purchasePrice: "",
        salesPrice: "",
        supplier: "",
        productsType: "",
        warehouseId: "",
        productDescription: "",
      },
      form1: {
        goods: "",
        goodsName: "",
        quantity: "",
        userName: "",
        userId: "",
        adminId: "",
        remark: "",
        action: "1",
      },
      rules: {
        productName: [
          { required: true, message: "请输入商品名称", trigger: "blur" },
        ],
        productId: [
          { required: true, message: "请输入商品编号", trigger: "blur" },
        ],
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
    checkInTime(time) {
      console.log(time);
      const dt = new Date(time);
      const newHour = dt.getHours() + 8;
      dt.setHours(newHour);
      const intime = dt.toJSON();
     const intimedate=intime.slice(0,10);
      console.log(intime);
      this.$axios
        .post(this.$httpUrl + "/usertransactions/listInTime?intime=" + intimedate)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.timecheckTable = res.data;
            this.total = res.total;
          } else {
            alert("此刻暂无商品入库信息");
          }
        });
    },
    checkOutTime(time) {
      console.log(time);
      const dt = new Date(time);
      const newHour = dt.getHours() + 8;
      dt.setHours(newHour);
      const outtime = dt.toJSON();
     const outtimedate=outtime.slice(0,10);
      this.$axios
        .post(this.$httpUrl + "/usertransactions/listOutTime?outtime=" + outtimedate)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code === 200) {
            this.timecheckTable = res.data;
            this.total = res.total;
            console.log(this.timecheckTable);
          } else {
            alert("此刻暂无商品出库信息");
          }
        });
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    del(productId) {
      console.log(productId);

      this.$axios
        .get(this.$httpUrl + "/goodstype/del?productId=" + productId)
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
      this.centerDialogVisible2 = true;
      this.$nextTick(() => {
        //赋值到表单
        this.form.productId = row.productId;
        this.form.productName = row.productName;
        this.form.purchasePrice = row.purchasePrice;
        this.form.salesPrice = row.salesPrice;
        this.form.supplier = row.supplier;
        this.form.productsType = row.productsType;
        this.form.warehouseId = row.warehouseId;
        this.form.productDescription = row.productDescription;
      });
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
        this.form.productId = "";
      });
    },
    doSave() {
      this.$axios
        .post(this.$httpUrl + "/Products/save", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "添加成功！",
              type: "success",
            });
            this.centerDialogVisible = false;
            this.loadPost();
            this.resetForm();
          } else {
            this.$message({
              message: "添加失败！",
              type: "error",
            });
          }
        });
    },
    doMod() {
      this.$axios
        .post(this.$httpUrl + "/Products/update", this.form)
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.$message({
              message: "修改成功！",
              type: "success",
            });
            this.centerDialogVisible2 = false;
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
          if (this.form.productId) {
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
    formatInName(row){
        let temp = this.UserData.find((item) => {
        //  console.log(item)
        return item.id == row.instaff;
      });
      return temp ? temp.name : "";
    },
     formatOutName(row){
        let temp = this.UserData.find((item) => {
        //  console.log(item)
        return item.id == row.outstaff;
      });
      return temp ? temp.name : "";
    },
    formatProductsName(row){
      let temp=this.productsData.find((item)=>{
        return item.productId==row.productid;
      });
      return temp ? temp.productName: "";
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
      this.productName = "";
      this.warehouse= "";
      this.productsType = "";
    },
    loadUser(){
      this.$axios
        .get(this.$httpUrl + "/user/list")
        .then((res) => {
          console.log(res);
          if (res.status == 200) {
            this.UserData = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadPost() {
      this.$axios
        .post(this.$httpUrl + "/usertransactions/listPage", {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
            param: {
              productName: this.productName,
              warehouse:this.warehouse+'',
              roleId:this.user.roleId+'',
            },
        })
        .then((res) => res.data)
        .then((res) => {
          // console.log(res);
          if (res.code == 200) {
            this.tableData = res.data;
            this.total = res.total;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadStorage() {
      this.$axios
        .get(this.$httpUrl + "/Warehouses/list")
        .then((res) => res.data)
        .then((res) => {
          console.log(res);
          if (res.code == 200) {
            this.storageData = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadProducts(){
      this.$axios
        .get(this.$httpUrl + "/Products/list")
        .then((res) => {
          if (res.status == 200) {
            this.productsData = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadGoodstype() {
      this.$axios
        .get(this.$httpUrl + "/goodstype/list")
        .then((res) => res.data)
        .then((res) => {
          if (res.code == 200) {
            this.goodstypeData = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
    loadInventory() {
      this.$axios
        .get(this.$httpUrl + "/inventory/list")
        // .then((res)=>console.log(res))
        .then((res) => {
          //   console.log(res);
          if (res.status == 200) {
            this.timecheckTable = res.data;
          } else {
            alert("获取数据失败");
          }
        });
    },
  },
  beforeMount() {
    this.loadPost();
    this.loadStorage();
    this.loadGoodstype();
    this.loadUser();
    this.loadProducts()
  },
};
</script>

<style scoped>
.type {
  color: #000000;
  font-weight: 500;
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
  background: #baf6cb99;
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
  background-color: #c9eaf7;
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
</style>
