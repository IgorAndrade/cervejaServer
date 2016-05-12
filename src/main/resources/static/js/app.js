'use strict';

// Declare app level module which depends on filters, and services

angular.module('app', ['ui.router','ui.bootstrap','ngRoute','ngTable','restangular','ngStorage','ngFileUpload','User'])

.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise('/home');
	$stateProvider
		.state('home', {
	      url: '/home',
	      views: {
	      	'menu':{templateUrl: 'views/diretivas/menu.html'},
	        'content': {
	          templateUrl: 'views/home.html',
	          controller: 'HomeController'
	        }
	      }
	     
		})
		.state('adm', {
	      url: '/profile',
	      views: {
	      	'menu':{templateUrl: 'views/diretivas/menuAdm.html'},
	        'content': {
	          templateUrl: 'views/cervejas/listaCervejas.html',
	          controller: 'NovoController'
	        }
	      } 
		})
		.state('importar', {
	      url: '/importar',
	      views: {
	      	'menu':{templateUrl: 'views/diretivas/menuAdm.html'},
	        'content': {
	          templateUrl: 'views/cervejas/importarCervejas.html',
	          controller: 'CervejaController'
	        }
	      } 
		})
		.state('cervejasList', {
	      url: '/listar',
	      views: {
	      	'menu':{templateUrl: 'views/diretivas/menuAdm.html'},
	        'content': {
	          templateUrl: 'views/cervejas/listaCervejas.html',
	          controller: 'CervejaController'
	        }
	      } 
		})
		.state('cerveja', {
	      url: '/cerveja',
	      views: {
	      	'menu':{templateUrl: 'views/diretivas/menuAdm.html'},
	        'content': {
	          templateUrl: 'views/cervejas/Cervejas.html',
	          controller: 'CervejaController'
	        }
	      },
	      params: {
	    	  cerveja:null,
	    	  id:null
	      }
		})
		.state('cerveja:id', {
	      url: '/cerveja/:id',
	      views: {
	      	'menu':{templateUrl: 'views/diretivas/menuAdm.html'},
	        'content': {
	          templateUrl: 'views/cervejas/Cervejas.html',
	          controller: 'CervejaController'
	        }
	      } 
		})
})
.run(function($rootScope){
	//$rootScope.$on( "$routeChangeStart", function(event, next, current) {
		$rootScope.$on('$viewContentLoaded', init);
	//})
})


.config(function(RestangularProvider,$windowProvider) {
	var window = $windowProvider.$get();
	 RestangularProvider.setFullResponse(true);
	RestangularProvider.setBaseUrl("http://"+window.location.host+"/cerveja/service");
    RestangularProvider.setRestangularFields({
        id: 'id'
      });
    })


