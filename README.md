# connections-apps-loader

## Components in this projects:

1. web: `com.isw.kudos.external-apps-core`
2. ear wrapper: `com.isw.kudos.external-apps`

## Build process:

Minify `loader.js` into `loader.min.js`  
Export `com.isw.kudos.external-apps` as `.ear`

## Install process

Download latest .ear from [releases page](https://github.com/isw-kudos/connections-apps-loader/releases)

Set WebSphere variable to configure the apps/urls to open through the frame:

```
EXTERNAL_APPS_CONFIG =
{"[APP_NAME]":"[URL_OF_APP]","[APP_NAME_2]":"[URL_OF_APP_2]"}
```

where:  
`[APP_NAME]` is unique, without spaces or special characters  
`[URL_OF_APP]` is a web address, ie `https://google.com`

For example, here are 3 apps we load:

```
EXTERNAL_APPS_CONFIG =
{"ideas":"https://ideas.isw.net.au","buzzy":"https://buzzy.buzz","contacts":"https://apps.isw.net.au"}
```

#### Install Options:

a) Install EAR in WebSphere using default binding of `/apps`  
Load `https://connections.company.com/apps` to see all apps defined above.  
Each app will be available at this path, ie:

```
https://connections.company.com/apps/[APP-NAME]
```

AND/OR

b) Install EAR multiple times, with customised contextRoot  
This method will allow you to load the app without the `/apps` prefix.  
The context root must be in the format `/[APP_NAME]`

For example, install the Application as:  
Name: `Ideas Frame`  
Context root: `/ideas`  
The Ideas app will load at this path:  
`https://connections.company.com/ideas`

#### Customisations

The following WebSphere variables are supported:

| Variable name                      | Purpose                                                               | Example                                                                                                                            |
| ---------------------------------- | --------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------- |
| EXTERNAL_APPS_AUTH_FAILURE_MESSAGE | Message to be displayed when user is not authorised to load the frame | `Sorry, you are not authorised/licenced to access this application. Please contact your administrator.` </br>(this is the default) |
| EXTERNAL_APPS_CONFIG               | URLs of the supported Apps                                            | `{"boards":"https://dev-client.isw.net.au"}`                                                                                       |
| EXTERNAL_APPS_CSS_INLINE           | CSS to be applied on the app loader                                   | `.lotusSearch { display: none; }`                                                                                                  |
| EXTERNAL_APPS_CSS_URL              | URL of a CSS file to load on the page                                 | `https://my-server.example.com/app-custom.css`                                                                                     |

Security roles:

| Role     | Purpose                                                                                        | Default             |
| -------- | ---------------------------------------------------------------------------------------------- | ------------------- |
| Everyone | Currently unused. Might be useful for accessing resources in the future                        | `Everyone`          |
| Access   | Who is allowed to access the frame. Set this to any LDAP groups you want to restrict access to | `All Authenticated` |
