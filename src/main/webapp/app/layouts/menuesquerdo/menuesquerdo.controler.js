(function() {
    'use strict';

    angular
        .module('projeto1App')
        .controller('Menuesquerdocontroler', MenuEsquerdoControler);

    MenuEsquerdoControler.$inject = ['$scope'];

    function MenuEsquerdoControler ($scope) {
        alert("oi");
      var vm = this;
      var $body = $('body');
      var $openCloseBar = $('.navbar .navbar-header .bars');

        $scope.$on('authenticationSuccess', function() {
            avaliaW();
        $(window).resize(function () {
            $.AdminBSB.leftSideBar.setMenuHeight();
            avaliaW();
        });

        });

        function avaliaW(){
            if ($body.width() > 1170){
                abrirMenu();
            }else{
                fecharMenu();
            }
        }

        function abrirMenu() {
          $body.removeClass('ls-closed');
          $openCloseBar.fadeOut();
        }

        function fecharMenu() {
            $body.addClass('ls-closed');
            $openCloseBar.fadeIn();
        }
    }
})();
