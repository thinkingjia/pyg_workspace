 //控制层 
app.controller('goodsController' ,function($scope,$controller,itemCatService   ,goodsService){	
	
	$controller('baseController',{$scope:$scope});//继承

	// 向对象中赋值和取值的方式
	$scope.itemCat={};
    // $scope.itemCat.id=12;  ---->itemCat={id:12}
    // $scope.itemCat['id']=12;  ---->itemCat={id:12}
    // $scope.itemCat[558]="手机";  //---->itemCat={558:"手机"}
    // alert($scope.itemCat[558]);

    $scope.findAllItemCat=function () {
        itemCatService.findAll().success(function (response) {
            // [{"id":1,"name":"图书、音像/电子书刊","parentId":0,"typeId":35},
			// 	{"id":2,"name":"电子书刊","parentId":1,"typeId":35}，
			// {"id":558,"name":"手机","parentId":0,"typeId":35}]  ----->{1:"图书、音像/电子书刊",2:"电子书刊",558:"手机"}
            for (var i = 0; i < response.length; i++) {
                $scope.itemCat[response[i].id ]=response[i].name;
            }
            // alert($scope.itemCat);
        })
    }
 
    $scope.updateAuditStatus=function (auditStatus) {
    	// update tb_goods set audit_status=? where id=?
        goodsService.updateAuditStatus($scope.selectIds,auditStatus).success(function (response) {
        	if(response.success){
        		$scope.reloadList();
        		$scope.selectIds=[];
			}else{
        		alert(response.message);
			}
        })
    }
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
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
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
});	
