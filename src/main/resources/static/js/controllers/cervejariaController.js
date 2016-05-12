'use strict';

/* Controllers */

angular.module('app').
  controller('CervejaController', function ($scope,$state,$stateParams,$q,CervejaModel,Restangular,Upload,NgTableParams) {

    $scope.cerveja= new CervejaModel();
    
	$scope.salvar = salvarCerveja;

	$scope.pesquisar=pesquisar;
	
	$scope.importar = importar;
	
	$scope.openCerveja = openCerveja;
	
	$scope.tabela = new NgTableParams({
		count: 10 
	}, {
			counts: [5,10,15,20,50],
			getData: popularListaCerv,
	        paginationMaxBlocks: 5,
	        paginationMinBlocks: 2
		}
	);
	$scope.listaStatus=[];
	Restangular.all("/cerveja/status").getList().then(function(result){
		var lista = result.data.plain();
		for(var f in result.data.plain())
		$scope.listaStatus.push({id:lista[f],title:lista[f]});
	})
	
	
    if($state.params && ($state.params.id || $state.params.cerveja)){
    	editar($state.params.id,$state.params.cerveja);
    }
    
    function editar(id,c){
    	if(c!=null)
    		$scope.cerveja= c;
    	else
    	Restangular.one("/cerveja",$state.params.id).get().then(function(novaCerveja){
    		$scope.cerveja= novaCerveja.data;
    	}).catch(function(e){
    		var a = e
    	})
    }
    	 
    function pesquisar(){
    	Restangular.all("/cerveja").customGET("pesquisar",{q:$scope.query}).then(
    	        function(result){
    	        	$scope.cervejas=result.data;
    	        },
    	        function(error){
    	        	$scope.cervejas=[];
    	        }
        	);
    };
    
    function popularListaCerv(param){
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
			Restangular.all("/cerveja").customGET(null,q,null).then( 
					function(result) {
						var r = result.data;
						param.total(r.totalElements);
						deferred.resolve(r.content);
					}).catch(function(e){
						deferred.resolve([]);
					});
			return deferred.promise 
		};

    function saveOk(result){
      $state.go("cervejasList");
    };
    function saveErro(result){

    };

  
  		
  	function salvarCerveja() {
        if ($scope.cerveja) {
          if($scope.rotulo)
//            upload(getNameImg("",$scope.cerveja),"cerveja",function(){
              enviarCerveja(upload);
//            });
          else
        	  enviarCerveja();
                	  
        }
    };

    function openCerveja(cerveja){
    	$state.go("cerveja:id",{id:cerveja.id});
    }
    
    function importar(cerveja){
      Restangular.one("/cerveja/importar",cerveja.brewerydbId).get()
        .then(function(novaCerveja){
        	openCerveja(novaCerveja.data);
      }).catch(function(e){
    	  var a = e;
      })
    };

    function getNameImg(prefix,cerveja){
        return prefix+"_"+cerveja.name.split(" ")[0];
    };

    function enviarCerveja(cb){
    	if(!cb)
    		cb=saveOk;
      if($scope.cerveja.id){
    	 $scope.cerveja.put().then(cb,saveErro); 
      }else
        Restangular.all("/cerveja").post($scope.cerveja).then(cb,saveErro);  
    };

    function upload(result) {
      if($scope.rotulo){
        var dados = {rotulo: $scope.rotulo, 'id': result.data.id};
        if($scope.garrafa)
          dados.garrafa=$scope.garrafa
          if($scope.outros)
        	  dados.outros=$scope.outros
        Upload.upload({
            url: 'service/cerveja/upload',
            arrayKey: '',
           data: dados
           // data: {file: file}
        }).then(saveOk, saveErro);
      }
    };

  })
