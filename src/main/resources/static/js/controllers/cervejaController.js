'use strict';

/* Controllers */

angular.module('app').
  controller('CervejaController', function ($scope,$state,$stateParams,CervejaModel,Restangular,Upload,ShareData) {

    $scope.cerveja= new CervejaModel();
    
	$scope.salvar = salvarCerveja;

	$scope.pesquisar=pesquisar;
	
	$scope.importar = importar;
	
	
	popularStyles();
	
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
    
    function popularStyles(){
    	Restangular.all("/style").getList().then(
			function(lista) {
				$scope.estilos = lista.data;
		});
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

    function importar(cerveja){
      Restangular.one("/cerveja/importar",cerveja.brewerydbId).get()
        .then(function(novaCerveja){
        	//ShareData.add(novaCerveja.id,novaCerveja);
        	 $state.go("cerveja",{id:novaCerveja.data.id});
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
