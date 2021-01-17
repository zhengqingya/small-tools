<!--
 * @Description:
 * @Version: 1.0
 * @Autor: yanxin
 * @Date: 2020-08-03 15:32:02
 * @LastEditors: yanxin
 * @LastEditTime: 2020-08-04 15:36:26
-->
<script>
export default {
  props: {
    title: {
      type: String,
      default: ""
    },
    column: {
      type: Number,
      default: 3
    },
    border: {
      type: Boolean,
      default: true
    },
    layout: {
      type: String,
      default: "horizontal"
    }
  },
  data() {
    return {};
  },
  // eslint-disable-next-line no-unused-vars
  render: function(h) {
    let children = this.$slots["default"];
    let column = this.column;
    let accArr = []; //累积的列
    let accNum = 0; //累积的列占用的span

    //计算每一行占多少列
    let rowArr = children.reduce((acc, item, index) => {
      accArr.push(item);
      accNum += 1;
      //所占单元大于等于全部单元,则清空累积的项，或者循环到最后一项时
      if (accNum >= column || index >= children.length - 1) {
        acc.push(accArr);
        accArr = [];
        accNum = 0;
      }

      return acc;
    }, []);
    let title = this.title;
    let boderClass = this.border ? "description-border" : "";
    return (
      <div class={["description", boderClass]}>
        {title ? <div class="description-list-title">{title}</div> : ""}

        <div class="description-view">
          <table>
            <tbody>
              {rowArr.map(item => {
                return <tr class="description-row">{item}</tr>;
              })}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
};
</script>
<style lang="scss" scoped>
.description-list-title {
  margin-bottom: 16px;
  color: #17233d;
  font-weight: 500;
  font-size: 14px;
}
:not(.description-border .description-row) {
  > td,
  > th {
    padding-bottom: 16px;
  }
}
.description-view {
  width: 100%;
  overflow: hidden;
  border-radius: 4px;
  > table {
    width: 100%;
    border-collapse: collapse;
  }
}
.description-border {
  border: 1px solid #e8e8e8;
  .description-row {
    border-bottom: 1px solid #e8e8e8;
    &:last-child {
      border-bottom: none;
    }
  }
}
</style>
