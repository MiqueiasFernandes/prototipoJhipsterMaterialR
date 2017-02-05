(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', '$scope'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService, $scope) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
            fecharMenu(true);
        }


        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }


var $body = $('body');
      var $openCloseBar = $('.navbar .navbar-header .bars');

      if(vm.isAuthenticated()){
          abrirMenu();
      }

$scope.$on('authenticationSuccess', function() {
            abrirMenu();
        });

 $(window).resize(function () {
     console.log("alter");
             abrirMenu();
             fecharMenu(false);
         });



 function abrirMenu() {
     if(vm.isAuthenticated() && $body.width() > 1170){
         $.AdminBSB.leftSideBar.setMenuHeight();
          $body.removeClass('ls-closed');
          $openCloseBar.fadeOut();
        }
 }


        function fecharMenu(force) {
            if((force || $body.width() < 1170 ) && !$body.hasClass('ls-closed')){
            $body.addClass('ls-closed');
            $openCloseBar.fadeIn();
            }
        }

    }
})();
