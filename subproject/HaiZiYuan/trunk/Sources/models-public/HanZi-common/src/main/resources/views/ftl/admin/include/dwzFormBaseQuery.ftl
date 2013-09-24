
	<input type="hidden" name="pageNum" value="${query.pageNum!"1"}" /><!--【必须】value=1可以写死-->
	<input type="hidden" name="numPerPage" value="${query.numPerPage!"20"}" /><!--【可选】每页显示多少条-->
	<input type="hidden" name="orderField" value="${query.orderField!""}" /><!--【可选】查询排序-->
    <input type="hidden" name="orderDirection" value="${query.orderDirection!""}" /><!--【可选】排序方式-->
    
    