/**
 * System configuration for Angular 2 samples
 * Adjust as necessary for your application needs.
 */
(function(global) {
  global.ENV = global.ENV || 'development';

  // map tells the System loader where to look for things
  var map = {
    'app':                        'src/tmp/app', // 'dist',
    'test':                       'src/tmp/test',
    '@angular':                   'node_modules/@angular',
    'ng2-bs3-modal':              'node_modules/ng2-bs3-modal',
    'rxjs':                       'node_modules/rxjs',
    'angular2-cookie':            'node_modules/angular2-cookie',
    'angular2-jwt':               'node_modules/angular2-jwt/angular2-jwt'
  };
  // packages tells the System loader how to load when no filename and/or no extension
  var packages = {
    'app': {
        defaultExtension: 'js'
    },
    'test': {
        defaultExtension: 'js'
    },
    'rxjs': {
        defaultExtension: 'js'
    },
    'angular2-cookie': {
        defaultExtension: 'js'
    },
    'ng2-bs3-modal': {
        defaultExtension: 'js'
    },
    'angular2-jwt': {
        defaultExtension: 'js'
    }
  };
  var ngPackageNames = [
    'common',
    'compiler',
    'core',
    'http',
    'platform-browser',
    'platform-browser-dynamic',
    'router',
    'router-deprecated',
    'upgrade',
  ];
  // Individual files (~300 requests):
  function packIndex(pkgName) {
    packages['@angular/'+pkgName] = { main: 'index.js', defaultExtension: 'js' };
  }
  // Bundled (~40 requests):
  function packUmd(pkgName) {
    packages['@angular/'+pkgName] = { main: '/bundles/' + pkgName + '.umd.js', defaultExtension: 'js' };
  }
  // Most environments should use UMD; some (Karma) need the individual index files
  var setPackageConfig = System.packageWithIndex ? packIndex : packUmd;
  // Add package entries for angular packages
  ngPackageNames.forEach(setPackageConfig);
  var config = {
    defaultJSExtensions: true,
    map: map,
    packages: packages
  };
  System.config(config);
})(this);
