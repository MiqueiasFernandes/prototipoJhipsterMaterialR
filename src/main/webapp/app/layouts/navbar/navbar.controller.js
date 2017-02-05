(function () {
    'use strict';

    angular
        .module('projeto1App')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', '$scope'];

    function NavbarController($state, Auth, Principal, ProfileService, LoginService, $scope) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function (response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.account = null;
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
            getAccount();
        }

        function dashboard(){
            $state.go('dashboard');
        }


        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }


        var $body = $('body');
        var $openCloseBar = $('.navbar .navbar-header .bars');

        fecharMenu(true);
        if (vm.isAuthenticated()) {
            abrirMenu();
        }

        $scope.$on('authenticationSuccess', function () {
            abrirMenu();
            getAccount();
        });

        $(window).resize(function () {
            abrirMenu();
            fecharMenu(false);
        });



        function abrirMenu() {
            if (vm.isAuthenticated() && $body.width() > 1170) {
                $.AdminBSB.leftSideBar.setMenuHeight();
                $body.removeClass('ls-closed');
                $openCloseBar.fadeOut();
            }
        }


        function fecharMenu(force) {
            if ((force || $body.width() < 1170) && !$body.hasClass('ls-closed')) {
                $body.addClass('ls-closed');
                $openCloseBar.fadeIn();
            }
        }

        //Collapse or Expand Menu
        $('.menu-toggle').on('click', function (e) {
            var $this = $(this);
            var $content = $this.next();

            if ($($this.parents('ul')[0]).hasClass('list')) {
                var $not = $(e.target).hasClass('menu-toggle') ? e.target : $(e.target).parents('.menu-toggle');

                $.each($('.menu-toggle.toggled').not($not).next(), function (i, val) {
                    if ($(val).is(':visible')) {
                        $(val).prev().toggleClass('toggled');
                        $(val).slideUp();
                    }
                });
            }

            $this.toggleClass('toggled');
            $content.slideToggle(320);
        });


        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        getAccount();

        Waves.attach('.menu .list a', ['waves-block']);
        Waves.init();

    }
})();
