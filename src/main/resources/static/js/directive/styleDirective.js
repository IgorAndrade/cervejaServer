(function() {
	'use strict';

	angular.module('app').directive("styleDlg", stylefnc);

	function stylefnc() {
		return {
		    restrict: 'E',
		    scope: {
		      retornoStyle: '=retorno',
		      id:'@stId'
		    },
		    templateUrl: 'views/cervejas/styleDialog.html',
		    controller: styleCtrl,
		    controllerAs: 'stCtrl',
		  };
		  
		function styleCtrl($scope,$q,Restangular,NgTableParams,$uibModal){
			var self = this;
			self.openStyle = openStyle;
			
			function openStyle(){
				var modal = $uibModal.open({
				      animation: true,
				      templateUrl: 'modalStyle',
				      controller: styleDlgCtrl,
				      controllerAs:'dlgCtrl'
				    });
			
			
				modal.result.then(function (selectedItem) {
					$scope.retornoStyle= selectedItem;
			    }, function () {
				      
				    });
			}
		}
		  
		  
	}
	
	function styleDlgCtrl($scope,$q,Restangular,NgTableParams,$uibModalInstance){
		$scope.tabela = new NgTableParams({
			count: 5
		}, {
				counts: [5,10,15],
				getData: popularStyles,
		        paginationMaxBlocks: 5,
		        paginationMinBlocks: 2
			}
		);
		
		$scope.click=click;
		
		function click(s){
			$uibModalInstance.close(s);
		}
		
		function popularStyles(param){
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
			Restangular.all("/style").customGET(null,q,null).then( 
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