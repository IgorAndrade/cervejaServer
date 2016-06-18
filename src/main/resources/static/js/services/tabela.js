(function() {
	'use strict';

	angular.module('app').factory("Tabela", navaTabela);

	function navaTabela(NgTableParams,$q,Restangular) {
		return function NovaTabela(url){
			var tabela = new NgTableParams({
				count: 10 
			}, {
					counts: [5,10,15,20,50],
					getData: getData,
			        paginationMaxBlocks: 5,
			        paginationMinBlocks: 2
				}
			);
			
			function getData(param){
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
				Restangular.all(url).customGET(null,q,null).then( 
						function(result) {
							var r = result.data;
							param.total(r.totalElements);
							deferred.resolve(r.content);
						}).catch(function(e){
							deferred.resolve([]);
						});
				return deferred.promise;
			};
			
			return tabela;
			
		}
	}

})();