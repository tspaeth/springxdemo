/**
 * @ngdoc overview
 * @name springbeer
 * @description Main file
 *
 * @author Thorsten Spaeth <info@conserata.com>
 *
 */
(function () {
    'use strict';
    angular.module('springbeer', ['ui.router','ui.grid','ui.bootstrap','pascalprecht.translate', 'LocalStorageModule'])
        .config(function ($stateProvider, $urlRouterProvider, $translateProvider, $httpProvider, localStorageServiceProvider) {

            /*
             * Locale storage setup for saving the token
             */
            localStorageServiceProvider
                .setPrefix('springbeer');
            /*
             * routes / state definitions for ui-router
             */
            $urlRouterProvider
                .otherwise('/');
            $stateProvider
                .state('home', {
                    url: '/',
                    templateUrl: 'views/home.html',
                    controller: 'HomeCtrl'
                })
                .state('login', {
                    url: '/login',
                    templateUrl: 'views/login.html',
                    controller: 'LoginCtrl'
                })
                .state('details', {
                    url: '/beer/:beerId',
                    templateUrl: 'views/details.html',
                    controller: 'DetailCtrl'
                })
                .state('createBeer', {
                    url: '/create',
                    templateUrl: 'views/edit.html',
                    controller: 'DetailCtrl'
                });


            /*
             * Translation initialization
             */
            $translateProvider
                .translations('en', {
                    TITLE: 'Beer database',
                    OVERVIEW: 'Overview',
                    'home': 'Home',
                    'CREATE_BEER': 'Create Beer',
                    'Login': 'Login',
                    'Logout': 'Logout',
                    'LIST_FILTER': 'Filter the list'
                })
                .translations('de', {
                    TITLE: 'Bierdatenbank',
                    OVERVIEW: 'Überblick',
                    'home': 'Start',
                    'CREATE_BEER': 'Neues Bier',
                    'Login': 'Anmelden',
                    'Logout': 'Abmelden',
                    'LIST_FILTER': 'Liste filtern'
                })
            $translateProvider.use('en');

            /*
             * Register one http request handler / interceptor for our application
             */
            $httpProvider.interceptors.push('beerHttpInterceptor');

        })
        .run(['$state', '$rootScope', function ($state, $rootScope) {
            console.log('running App');

            $rootScope.$on(':error:denied', function (data) {
                console.log('errror');
                $state.go('login');
            })
        }])
        .constant('RESTBASE',{
            'URL': 'http://localhost:8080/server'
        })
})();