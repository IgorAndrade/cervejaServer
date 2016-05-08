(function() {
	'use strict';

	angular.module('app').directive("cervejariaDlg", stylefnc);

	function stylefnc() {
		return {
		    restrict: 'E',
		    scope: {
		      retorno: '=retorno'
		    },
		    templateUrl: 'views/cervejas/cervejariaDialog.html',
		    controller: cervejariaCtrldlg,
		    controllerAs: 'cervCtrl',
		  };
		  
		function cervejariaCtrldlg($scope,$q,Restangular,NgTableParams,$uibModal){
			var self = this;
			self.open = open;
			
			function open(){
				var modal = $uibModal.open({
				      animation: true,
				      templateUrl: 'modalCervejaria',
				      controller: cervejariaCtrldlgBack,
//				      controllerAs:'cervejariaDlgCtrl'
				    });
			
			
				modal.result.then(function (selectedItem) {
					$scope.retorno= selectedItem;
			    }, function () {
				      
				    });
			}
		}
		  
		  
	}
	
	function cervejariaCtrldlgBack($scope,$q,Restangular,NgTableParams,$uibModalInstance){
		$scope.tabela = new NgTableParams({
			count: 5
		}, {
				counts: [5,10,15],
				getData: popular,
		        paginationMaxBlocks: 5,
		        paginationMinBlocks: 2
			}
		);
		
		$scope.click=click;
		
		function click(s){
			$uibModalInstance.close(s);
		}
		
		function popular(param){
			var q={
				pg:param.page()-1,
				qtd:param.count()
			};

			if(param.filter()){
				for(var f in param.filter())
					q[f]=param.filter()[f];
			}
			if(param.sorting()){
				for(var s in param.sorting())
					q[s]=param.sorting()[s];
			}
			
			var deferred = $q.defer();
			Restangular.all("/cervejaria").customGET(null,q,null).then( 
					function(result) {
						var r = result.data;
						param.total(r.totalElements);
						deferred.resolve(r.content);
					}).catch(function(e){
						deferred.resolve([]);
					});
			return deferred.promise 
		};
	}
})();