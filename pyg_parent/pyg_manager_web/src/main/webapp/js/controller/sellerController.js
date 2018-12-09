 //控制层 
app.controller('sellerController' ,function($scope,$controller   ,sellerService){	
	
	$controller('baseController',{$scope:$scope});//继承



	$scope.updateStatus=function(status,info){
		var flag = window.confirm("确认要"+info+"此商家吗？");
		if(flag){
            // update tb_seller set   status=? where seller_id=?
			sellerService.updateStatus(status,$scope.entity.sellerId).success(function(response){
				if(response.success){
					$scope.reloadList();
				}else{
					alert(response.message);
				}
			})
		}
	}
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		sellerService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		sellerService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		sellerService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=sellerService.update( $scope.entity ); //修改  
		}else{
			serviceObject=sellerService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		sellerService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	//搜索
	$scope.search=function(page,rows){
            sellerService.search(page,rows,$scope.searchEntity).success(
                function(response){
                    $scope.list=response.rows;
                    $scope.paginationConf.totalItems=response.total;//更新总记录数
                }
            );
	}

    // 全选当前页的所有数据
    $scope.checkAll=function (event) {
        // $scope.selectIds=[];
        if(event.target.checked){
            for (var i = 0; i < $scope.list.length; i++) {
                if(! $scope.ifChecked( $scope.list[i].sellerId)) { //如果返回false代表没有勾选
                    $scope.selectIds.push( $scope.list[i].sellerId);
                }
            }
        }else{  //取消勾选当前页的数据  就是把当前页数据的每个id从数组中移除
            // $scope.selectIds=[];
            for (var i = 0; i < $scope.list.length; i++) {
                var index =  $scope.selectIds.indexOf( $scope.list[i].sellerId);
                $scope.selectIds.splice(index,1 );
            }
        }
    }

    $scope.ifCheckedAll=function () { //判断当前页的list数据的每个对象中的id是否都存在数组中。但凡有一个不存在 此方法直接返回false
        for (var i = 0; i < $scope.list.length; i++) {
            var index =  $scope.selectIds.indexOf( $scope.list[i].sellerId);
            if(index==-1){
                return false;
            }
        }
        return true;
    }
    
});	
