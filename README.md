# connections-apps-loader

Two components to this projects:  
1. web: com.isw.kudos.external-apps-core
2. ear wrapper: com.isw.kudos.external-apps  
</br>

Build process:
1. Minify loader.js into loader.min.js  
2. Export com.isw.kudos.external-apps as .ear  
3. Set WebSphere variable to set extensions/urls to show  
EXTERNAL_APPS_CONFIG =  
```{"ideas":"https://innovation-dev.isw.net.au","buzzy":"https://buzzy.buzz","contacts":"https://apps.isw.net.au"}```  
4. Install as app in WebSphere (default binding is /apps)  
Load https://connections.isw.net.au/apps
