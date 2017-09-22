# connections-apps-loader

Two parts in this projects:
1. Web components: com.isw.kudos.external-apps-core
2. .ear wrapper: com.isw.kudos.external-apps

Build process:
Minify loader.js into loader.min.js
Export com.isw.kudos.external-apps as .ear
Set WebSphere variable of WebSites to show
- EXTERNAL_APPS_CONFIG = ```{"ideas":"https://innovation-dev.isw.net.au","buzzy":"https://buzzy.buzz","contacts":"https://apps.isw.net.au"}```

Install as app in WebSphere (default binding is /apps)
Load https://connections.isw.net.au/apps
