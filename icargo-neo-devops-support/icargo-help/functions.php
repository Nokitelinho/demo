<?php
/**
* KnowAll theme functions and definitions
* by HeroThemes (https://herothemes.com)
*/

// Enable to use unminfied scripts
if ( ! defined( 'KNOWALL_DEBUG_SCRIPTS' ) ) {
	define( 'KNOWALL_DEBUG_SCRIPTS', false );
}

// Documentation/Support URL
if ( ! defined( 'HT_KA_SUPPORT_URL' ) ) {
	define( 'HT_KA_SUPPORT_URL', 'https://herothemes.com/kadocs/' );
}

// Gravity Forms info url
if ( ! defined( 'HT_GF_INFO_URL' ) ) {
	define( 'HT_GF_INFO_URL', 'https://herothemes.com/gfinfo/' );
}

/**
* Sets up theme defaults and registers support for various WordPress features.
*/
if ( ! function_exists( 'ht_theme_setup' ) ) :
	function ht_theme_setup() {

		/**
		* Make theme available for translation
		* Translations can be filed in the /languages/ directory
		*/
		load_theme_textdomain( 'knowall', get_template_directory() . '/languages' );

		/**
		* Add default posts and comments RSS feed links to head
		*/
		add_theme_support( 'automatic-feed-links' );

		/**
		* Enable support for Post Thumbnails
		*/
		add_theme_support( 'post-thumbnails' );
		set_post_thumbnail_size( 60, 60 );
		add_image_size( 'hkb-thumb', 100, 100, false ); // KB Category Icons: Max height/width set to 100x100

		/**
		* Register menu locations
		*/
		register_nav_menus(
			array(
				'nav-site-header' => __( 'Site Header Menu', 'knowall' ),
				'nav-site-footer' => __( 'Site Footer Menu', 'knowall' ),
			)
		);

		/*
		* Let WordPress manage the document title.
		* By adding theme support, we declare that this theme does not use a
		* hard-coded <title> tag in the document head, and expect WordPress to
		* provide it for us.
		*/
		add_theme_support( 'title-tag' );

		// Enable HTML5 markup
		add_theme_support(
			'html5',
			array(
				'comment-list',
				'comment-form',
				'search-form',
				'gallery',
				'caption',
			)
		);

		// Add theme support for selective refresh for widgets.
		add_theme_support( 'customize-selective-refresh-widgets' );

		// Add support for editor styles.
		add_theme_support( 'editor-styles' );

		// Enqueue editor styles.
		add_editor_style( 'css/editor-style.css' );

		// Add support for Block Styles.
		add_theme_support( 'wp-block-styles' );

		// Add support for responsive embedded content.
		add_theme_support( 'responsive-embeds' );

		// This theme uses its own gallery styles.
		add_filter( 'use_default_gallery_style', '__return_false' );

		// This is a HeroTheme
		add_theme_support( 'herotheme' );

		// This theme supported HKB category icons
		add_theme_support( 'ht-kb-category-icons' );

		// HT managed updates
		add_theme_support( 'ht-kb-theme-managed-updates' );

		//ht shortcodes support (legacy)
		add_theme_support( 'ht-shortcodes', 'accordion', 'messages', 'tabs', 'toggle' );

		//setting filters moved to inc/setting-filters

		//remove knowledge base plugin registered sidebars
		add_filter( 'ht_kb_home_register_sidebar', '__return_false' );
		add_filter( 'ht_kb_taxonomy_register_sidebar', '__return_false' );
		add_filter( 'ht_kb_article_register_sidebar', '__return_false' );

		// Add custom colors to Gutenberg color palette

		add_theme_support( 
			'editor-color-palette', 
			array(
				array(
					'name'  => esc_html__( 'Black', 'knowall' ),
					'slug'  => 'black',
					'color' => '#000000',
				),
				array(
					'name'  => esc_html__( 'Dark gray', 'knowall' ),
					'slug'  => 'dark-gray',
					'color' => '#28303D',
				),
				array(
					'name'  => esc_html__( 'Gray', 'knowall' ),
					'slug'  => 'gray',
					'color' => '#39414D',
				),
			    array(
			        'name' => __( 'Main Theme Color', 'knowall' ),
			        'slug' => 'theme-default',
			        'color' => get_theme_mod( 'ht_setting__linkcolor', '#00b4b3' ),
			    ),
			    array(
			        'name' => __( 'Blue', 'knowall' ),
			        'slug' => 'blue',
			        'color' => '#0069e4',
			    ),
			    array(
			        'name' => __( 'Purple', 'knowall' ),
			        'slug' => 'purple',
			        'color' => '#5749a0',
			    ),
			    array(
			        'name' => __( 'Orange', 'knowall' ),
			        'slug' => 'orange',
			        'color' => '#f0644a',
			    ),
			    array(
			        'name' => __( 'Green', 'knowall' ),
			        'slug' => 'green',
			        'color' => '#46a162',
			    ),
			    array(
			        'name' => __( 'Pale Blue', 'knowall' ),
			        'slug' => 'pale-blue',
			        'color' => '#7994be',
			    ),
			    array(
			        'name' => __( 'White', 'knowall' ),
			        'slug' => 'white',
			        'color' => '#fff',
			    ),
			)
		);

	}
endif; // ht_theme_setup

add_action( 'after_setup_theme', 'ht_theme_setup' );

/**
 * Set the content width based on the theme's design and stylesheet.
 *
 */
if ( ! isset( $content_width ) ) {
	$content_width = 643;
}

/**
 * Enqueues styles for front-end.
 */
function ht_theme_styles() {

	/*
	 * Loads our main stylesheet.
	 */
	wp_enqueue_style( 'ht-theme-style', get_template_directory_uri() . '/css/style.css', false, get_ht_theme_version() );

}
add_action( 'wp_enqueue_scripts', 'ht_theme_styles' );


/**
 * Enqueues scripts for front-end.
 */

function ht_theme_scripts() {

	/*
	* Load our main theme JavaScript file
	*/
	$theme_js_file_src = ( KNOWALL_DEBUG_SCRIPTS ) ? get_template_directory_uri() . '/js/js.js' : get_template_directory_uri() . '/js/js.min.js';


	wp_enqueue_script( 'ht_theme_js', $theme_js_file_src, array( 'jquery' ), false, get_ht_theme_version() );

	wp_enqueue_script( 'scrollspy', get_template_directory_uri() . '/js/scrollspy.js', array( 'ht_theme_js' ), false, get_ht_theme_version() );

	wp_localize_script( 'scrollspy', 'scrollspyCustomValues', 
							array( 
								'offset' => apply_filters('ht_scroll_offset', 0)
							) );
	/*
	* Adds JavaScript to pages with the comment form to support
	* sites with threaded comments (when in use).
	*/
	if ( is_singular() && comments_open() && get_option( 'thread_comments' ) ) {
		wp_enqueue_script( 'comment-reply' );
	}

}
add_action( 'wp_enqueue_scripts', 'ht_theme_scripts' );

/**
 * Register widget area.
 *
 * @link http://codex.wordpress.org/Function_Reference/register_sidebar
 */
function _ht_widgets_init() {
	register_sidebar(
		array(
			'name'          => esc_html__( 'Sidebar -- Article', 'knowall' ),
			'id'            => 'sidebar-article',
			'description'   => ht_get_sidebar_description( 'sidebar-article' ),
			'before_widget' => '<section id="%1$s" class="widget %2$s">',
			'after_widget'  => '</section>',
			'before_title'  => '<h3 class="widget__title">',
			'after_title'   => '</h3>',
		)
	);
	register_sidebar(
		array(
			'name'          => esc_html__( 'Sidebar -- Category', 'knowall' ),
			'id'            => 'sidebar-category',
			'description'   => ht_get_sidebar_description( 'sidebar-category' ),
			'before_widget' => '<section id="%1$s" class="widget %2$s">',
			'after_widget'  => '</section>',
			'before_title'  => '<h3 class="widget__title">',
			'after_title'   => '</h3>',
		)
	);
	register_sidebar(
		array(
			'name'          => esc_html__( 'Sidebar -- Home', 'knowall' ),
			'id'            => 'sidebar-home',
			'description'   => ht_get_sidebar_description( 'sidebar-home' ),
			'before_widget' => '<section id="%1$s" class="widget %2$s">',
			'after_widget'  => '</section>',
			'before_title'  => '<h3 class="widget__title">',
			'after_title'   => '</h3>',
		)
	);

	register_sidebar(
		array(
			'name'          => esc_html__( 'Sidebar -- Page', 'knowall' ),
			'id'            => 'sidebar-page',
			'description'   => ht_get_sidebar_description( 'sidebar-page' ),
			'before_widget' => '<section id="%1$s" class="widget %2$s">',
			'after_widget'  => '</section>',
			'before_title'  => '<h3 class="widget__title">',
			'after_title'   => '</h3>',
		)
	);

	if ( apply_filters( 'ht_knowall_posts_functionality', false ) ) {
		register_sidebar(
			array(
				'name'          => esc_html__( 'Sidebar -- Blog', 'knowall' ),
				'id'            => 'sidebar-blog',
				'description'   => '',
				'before_widget' => '<section id="%1$s" class="widget %2$s">',
				'after_widget'  => '</section>',
				'before_title'  => '<h3 class="widget__title">',
				'after_title'   => '</h3>',
			)
		);
	}

}
add_action( 'widgets_init', '_ht_widgets_init' );

/*
* Sidebar Descriptions
*/
function ht_get_sidebar_description( $sidebar_id ) {
	$ht_sidebar_description = '';
	switch ( $sidebar_id ) {
		case 'sidebar-article':
			$ht_sidebar_description .= __( 'Add widgets here to appear in your sidebar on articles.', 'knowall' );
			break;
		case 'sidebar-category':
			$ht_sidebar_description .= __( 'Add widgets here to appear in your sidebar on category archives.', 'knowall' );
			break;
		case 'sidebar-home':
			$ht_sidebar_description .= __( 'Add widgets here to appear in your sidebar on the homepage.', 'knowall' );
			break;
		case 'sidebar-page':
			$ht_sidebar_description .= __( 'Add widgets here to appear in your sidebar on pages.', 'knowall' );
			break;
		case 'sidebar-blog':
			$ht_sidebar_description .= __( 'Add widgets here to appear in your sidebar on the blog.', 'knowall' );
			break;
		default:
			$ht_sidebar_description .= __( 'A sidebar area', 'knowall' );
			break;
	}

	$ht_sidebar_description = apply_filters( 'ht_sidebar_description', $ht_sidebar_description );

	$ht_sidebar_inactive_warning = '';

	if ( 'sidebar-article' == $sidebar_id && 'off' == get_theme_mod( 'ht_setting__articlesidebar', 'left' ) ) {
		$ht_sidebar_inactive_warning .= '<strong>This sidebar not active (full width layout), no widgets will appear. Change from Appearance > Customize > Theme > Article Settings > Article Sidebar Position</strong>';
	} elseif ( 'sidebar-category' == $sidebar_id && 'off' == get_theme_mod( 'ht_setting__acategorysidebar', 'right' ) ) {
		$ht_sidebar_inactive_warning .= '<strong>This sidebar not active (full width layout), no widgets will appear. Change from Appearance > Customize > Theme > Category Settings > Category Sidebar Position</strong>';
	} elseif ( 'sidebar-home' == $sidebar_id && 'off' == get_theme_mod( 'ht_setting__homepagesidebar', 'right' ) ) {
		$ht_sidebar_inactive_warning .= '<strong>This sidebar not active (full width layout), no widgets will appear. Change from Appearance > Customize > Theme > Homepage > Homepage Sidebar Position</strong>';
	} elseif ( 'sidebar-page' == $sidebar_id && 'off' == get_theme_mod( 'ht_setting__pagesidebar', 'off' ) ) {
		$ht_sidebar_inactive_warning .= '<strong>This sidebar not active (full width layout), no widgets will appear. Change from Appearance > Customize > Theme > Page > Page Sidebar Position</strong>';
	} elseif ( 'sidebar-blog' == $sidebar_id && 'off' == get_theme_mod( 'ht_setting__blogsidebar', 'right' ) ) {
		$ht_sidebar_inactive_warning .= '<strong>This sidebar not active (full width layout), no widgets will appear. Change from Appearance > Customize > Theme > Blog > Blog Sidebar Position</strong>';
	}
	$ht_sidebar_inactive_warning = apply_filters( 'ht_sidebar_inactive_warning', $ht_sidebar_inactive_warning );

	$sidebar_description = $ht_sidebar_description . $ht_sidebar_inactive_warning;
	$sidebar_description = apply_filters( 'sidebar_description', $sidebar_description );

	return $sidebar_description;
}


/**
 * Modify default excerpt length
 */
function ht_excerpt_length( $length ) {
	return apply_filters( 'ht_excerpt_length', 20 );
}
add_filter( 'excerpt_length', 'ht_excerpt_length', 999 );

/**
* Excerpt
* Reminder - hkb_get_the_excerpt filters is also available
*/
function ht_excerpt_more( $more ) {
	return apply_filters( 'ht_excerpt_more', __( '...', 'knowall' ) );
}
add_filter( 'excerpt_more', 'ht_excerpt_more' );

/**
* Comment Functions
*/
require( 'inc/comment-functions.php' );

/**
* Customizer
*/
require( 'inc/customizer.php' );

/**
* Updater
*/
require( 'inc/updater.php' );


/**
* Dashboard
*/
require( 'inc/dashboard.php' );


// TGM Config
require( 'inc/tgm-config.php' );

/**
* Template Tags
*/
require( 'inc/template-tags.php' );

/**
* Posts Functions
*/
require( 'inc/posts.php' );

/**
* Theme Welcome
*/
require( 'inc/welcome/knowall-welcome-admin.php' );

/**
* Translation Helpers
*/
require( 'inc/translation-helpers.php' );

/**
* Support Helpers
*/
require( 'inc/support-helpers.php' );

/**
* Template tags/helpers
*/
require( 'inc/template-functions.php' );

/**
* Blocks Compat
*/
require( 'inc/blocks.php' );

/**
* WooCommerce
*/
require( 'inc/woocommerce-compat.php' );

/**
* Setting Filters
*/
require( 'inc/setting-filters.php' );

//Change default widget tag cloud settings
function ht_set_tag_cloud_args( $args ) {
	$args['largest']  = 16;
	$args['smallest'] = 10;
	$args['unit']     = 'px';
	return $args;
}
add_filter( 'widget_tag_cloud_args', 'ht_set_tag_cloud_args' );

//KnowAll breadcrumbs filter
if ( ! function_exists( 'ht_ka_breadcrumbs_filter' ) ) {
	function ht_ka_breadcrumbs_filter( $ancestors ) {
		if(  !hkb_is_kb_set_as_front() && '0' != get_option( 'page_on_front' ) && !ht_kb_is_ht_kb_search()){
			//do nothing if the knowledge base is not the front page (and we're not displaying a search result)
			return $ancestors;
		}
		foreach ( $ancestors as $key => $path ) {
			foreach ( $path as $index => $item ) {
				//remove kb_home from ancestors
				if ( array_key_exists( 'type', $item ) && 'kb_home' == $item['type'] ) {
					unset( $ancestors[$key][$index] );
					//reindex
					$ancestors[$key] = array_values( $ancestors[$key] );
				}
			}
		}
		return $ancestors;
	}
}
add_filter( 'ht_kb_get_ancestors', 'ht_ka_breadcrumbs_filter', 10, 1 );

/**
* Redirect to Welcome setup on theme activation
*/
function ht_knowall_onthemeswitch_welcome_redirect() {
	if ( current_user_can( 'edit_theme_options' ) && apply_filters( 'ht_knowall_direct_to_welcome_onthemeswitch', true ) ) {
		//transfer to welcome installer
		wp_safe_redirect( admin_url( 'themes.php?page=knowall-welcome' ) );
	}
}
add_action( 'after_switch_theme', 'ht_knowall_onthemeswitch_welcome_redirect' );



/**
* Shiv to allow users to deactivate old helpguru plugin
* This does not work as show_ht_kb_welcome_on_activation filter is not available in HG KB 
*/
function ht_knowall_disable_helpguru_auto_activation($bool){
	$helpguru_old_kb_plugin = get_option( 'helpguru_old_kb_plugin', false );
	//disable auto activation if we've detected HelpGuru plugin
	if ( defined(HT_KB_VERSION_NUMBER) && defined(HT_KB_MAIN_PLUGIN_FILE) && version_compare( HT_KB_VERSION_NUMBER, '3.0', '>=' ) ) {
		add_filter('show_ht_kb_welcome_on_activation', '__return_false');
		if( $helpguru_old_kb_plugin ){ 
			deactivate_plugins( array( plugin_basename( $helpguru_old_kb_plugin ) ) );
			delete_plugins( array( plugin_basename( $helpguru_old_kb_plugin ) ) );
		}
		update_option( 'helpguru_old_kb_plugin', HT_KB_MAIN_PLUGIN_FILE );
		return false;
	}
	return $bool;
}
//add_filter('ht_knowall_allow_hkb_install', 'ht_knowall_disable_helpguru_auto_activation', 10);


/**
* Set customizer options after theme switch / on theme upgrade
*/
function ht_knowall_setup_customizer_options_upgrade() {
	$ht_setting__kbarchivestyle = get_theme_mod( 'ht_setting__kbarchivestyle', 0 );
	//if less than 7, this needs an upgrade
	$int_ht_setting__kbarchivestyle = intval( $ht_setting__kbarchivestyle );

	//if the kb archive style is less than 7, it needs to be set
	if ( $int_ht_setting__kbarchivestyle < 7 ) {
		switch ( $int_ht_setting__kbarchivestyle ) {
			case 1:
				set_theme_mod( 'ht_setting__kbarchivestyle', '7' );
				set_theme_mod( 'ht_setting__kbarchivecatdesc', '0' );
				break;
			case 2:
				set_theme_mod( 'ht_setting__kbarchivestyle', '7' );
				set_theme_mod( 'ht_setting__kbarchivecatdesc', '1' );
				break;
			case 3:
				set_theme_mod( 'ht_setting__kbarchivestyle', '8' );
				set_theme_mod( 'ht_setting__kbarchivecatdesc', '0' );
				break;
			case 4:
				set_theme_mod( 'ht_setting__kbarchivestyle', '8' );
				set_theme_mod( 'ht_setting__kbarchivecatdesc', '1' );
				break;
			case 5:
				set_theme_mod( 'ht_setting__kbarchivestyle', '9' );
				set_theme_mod( 'ht_setting__kbarchivecatdesc', '0' );
				break;
			case 6:
				set_theme_mod( 'ht_setting__kbarchivestyle', '9' );
				set_theme_mod( 'ht_setting__kbarchivecatdesc', '1' );
				break;

			default:
				set_theme_mod( 'ht_setting__kbarchivestyle', '7' );
				set_theme_mod( 'ht_setting__kbarchivecatdesc', '1' );
				break;
		}//end switch
	}//end if

	$ht_setting__kbarchivehideemptycats = get_theme_mod( 'ht_setting__kbarchivehideemptycats', false );
	if ( $ht_setting__kbarchivehideemptycats === false ) {
		set_theme_mod( 'ht_setting__kbarchivehideemptycats', '0' );
	}

}
//hook onto both theme switch and upgrade process
add_action( 'after_switch_theme', 'ht_knowall_setup_customizer_options_upgrade' );
add_action( 'upgrader_process_complete', 'ht_knowall_setup_customizer_options_upgrade' );

/**
* Display admin message when required plugins not activated
*/
function ht_knowall_tgm_admin_messages() {
	if ( current_user_can( 'edit_theme_options' ) ) {
		//if ht knowledge base and ht blocks don't exist, transfer to plugin installer
		if ( ! class_exists( 'HT_Knowledge_Base' ) || ! class_exists( 'HT_Blocks' ) ) {
			?>
				<div class="notice notice-success">
					<p>
						<?php printf( esc_html( 'KnowAll is almost ready, please install and activate the required plugins, once done you can <a href="%s">return to the dashboard</a>.', 'knowall' ), esc_url( admin_url() ) ); ?>
					</p>
				</div>
			<?php
		} //test classes exist
	} //current user can
}
//not required in this version
//add_action( 'admin_notices', 'ht_knowall_tgm_admin_messages' );

/**
* Adds theme name + version to the <head> tag
*/
function ht_themeversion_in_header() {
	echo __( '<meta name="generator" content="' . get_ht_theme_name() . ' v' . get_ht_theme_version() . '" />' . "\n", 'knowall' );
}
add_action( 'wp_head', 'ht_themeversion_in_header' );

/**
* Function for returning the theme name
*/
function get_ht_theme_name() {
	$ht_theme = wp_get_theme();
	return $ht_theme->get( 'Name' );
}

/**
* Function for returning parent theme version (auto-updater)
*/
function get_ht_theme_version() {
	$theme_data    = wp_get_theme();
	$theme_version = '';
	$is_child      = ht_is_child_theme( $theme_data );
	if ( $is_child ) {
		$theme_version = $theme_data->parent()->get( 'Version' );
	} else {
		$theme_version = $theme_data->get( 'Version' );
	}
	return $theme_version;
}

/**
* Is active theme child theme?
*/
function ht_is_child_theme( $theme_data ) {
	$parent = $theme_data->parent();
	if ( ! empty( $parent ) ) {
		return true;
	}
	return false;
}

/**
* KnowAll search post types filter
*/
function ht_knowall_search_post_types_kb_filter( $post_types ) {
	//add posts to search if support declared
	if ( apply_filters( 'ht_knowall_posts_functionality', false ) ) {
		$post_types[] = 'post';
	}
	return $post_types;
}
add_filter( 'hkb_search_post_types', 'ht_knowall_search_post_types_kb_filter', 10, 1 );

/**
* Set default HKB options
*/
add_filter( 'hkb_show_comments_display', '__return_true' );
add_filter( 'hkb_show_related_articles', '__return_true' );
add_filter( 'hkb_show_search_excerpt', '__return_true' );

/**
* Don't show welcome screen - use new welcome wizard
*/
add_filter( 'show_ht_kb_welcome_on_activation', '__return_false' );

/**
* Add a pingback url auto-discovery header for singularly identifiable articles.
*/
function ht_pingback_header() {
	if ( is_singular() && pings_open() ) {
		echo '<link rel="pingback" href="', bloginfo( 'pingback_url' ), '">' . "\n";
	}
}
add_action( 'wp_head', 'ht_pingback_header' );

/**
* KB archive articles
*/
function ht_knowall_hkb_archivearticles_num( $current_articlesnum ) {
	$ht_setting__kbarchivecatarticles_num = get_theme_mod( 'ht_setting__kbarchivecatarticles_num', '5' );
	return apply_filters( 'ht_knowall_hkb_archivearticles_num', $ht_setting__kbarchivecatarticles_num );
}
add_filter( 'hkb_home_articles', 'ht_knowall_hkb_archivearticles_num', 10, 1 );

/**
* Display a warning if the packaged plugins are out of date
*/
function ht_check_packaged_plugins_are_current() {
	$screen 			 = get_current_screen();
	$tgm_instance        = call_user_func( array( get_class( $GLOBALS['tgmpa'] ), 'get_instance' ) );
	$installed_plugins   = $tgm_instance->get_plugins();
	$out_of_date_plugins = array();
	
	//early exit if theme setup page
	if( !is_a( $screen, 'WP_Screen') || 'appearance_page_knowall-welcome' == $screen->base ){
		return;
	}
	
	foreach ( $tgm_instance->plugins as $tgm_slug => $tgm_data ) {
		foreach ( $installed_plugins as $ip_slug => $ip_data ) {
			//split the slug
			$ip_slug_array = explode( '/', $ip_slug );
			//check the slug
			if ( is_array( $ip_slug_array ) && isset( $ip_slug_array[0] ) && $ip_slug_array[0] == $tgm_slug ) {
				//match
				$plugin_name       = $ip_data['Name'];
				$installed_version = $ip_data['Version'];
				$tgm_version       = $tgm_data['version'];
				$plugin_indate     = version_compare( $installed_version, $tgm_version, '>=' );
				//plugin needs updating
				if ( ! $plugin_indate && current_user_can( 'update_plugins' ) && apply_filters( 'knowall_show_out_of_date_plugins_warning', true ) ) {
					?>
						<div class="notice notice-warning">
							<p>
								<?php printf( __( '<strong>Warning</strong> The %1$s plugin is out of date, please <a href="%2$s">update now</a>. (Installed version is <strong>%3$s</strong>, requires version <strong>%4$s</strong>)', 'knowall' ), $plugin_name, admin_url( 'themes.php?page=knowall-welcome' ), $installed_version, $tgm_version ); ?>
							</p>
						</div>
					<?php
				}
			}
		}
	}
}
add_action( 'admin_notices', 'ht_check_packaged_plugins_are_current' );

/**
* Display a warning if the wp version does not meet the themes Requires at least config
*/
function ht_check_required_wp_version(){
	global $wp_version;
	
	$theme_info = wp_get_theme();

	//extracted from style.css
	$theme_required_wp_version = is_a( $theme_info, 'WP_Theme' ) ? $theme_info->get( 'RequiresWP' ) : false;

	if( !$theme_required_wp_version ){
		//early exit if we can't get data
		return;
	} 
	if ( version_compare( $wp_version, $theme_required_wp_version , '>=' ) ) {
		//early exit if wp version satitated
		return;
	}
	?>
	<div class="notice notice-warning">
		<p>
			<?php printf( __( '<strong>Warning</strong> The current version of WordPress does not meet the requirements for this theme, please <a href="%2$s">upgrade WordPress</a> for full support. (WordPress version is <strong>%3$s</strong>, requires version <strong>%4$s</strong>)', 'knowall' ), false, admin_url( 'update-core.php' ), $wp_version, $theme_required_wp_version ); ?>
		</p>
	</div>
	<?php
}
add_action( 'admin_notices', 'ht_check_required_wp_version' );


/**
* Display a warning if no categories
*/
function ht_check_kb_category_count(){

	//early exit if plugin not active
	if( !function_exists('hkb_get_archive_tax_terms') ){
		return;
	}

	//early exit if theme setup page
	$screen = get_current_screen();
	if( !is_a( $screen, 'WP_Screen') || 'appearance_page_knowall-welcome' == $screen->base ){
		return;
	}

	//early exit if tax terms detected
	$ht_kb_categories = hkb_get_archive_tax_terms();
	if( $ht_kb_categories && count($ht_kb_categories) > 0 ){
		return;
	}

	?>
	<div class="notice notice-warning">
		<p>
			<?php printf( __( '<strong>Warning</strong> No Knowledge Base Article Categories detected, please <a href="%s">complete the KnowAll setup</a>, or <a href="%s">add your own article categories</a>.', 'knowall' ), admin_url( 'themes.php?page=knowall-welcome' ), admin_url( 'edit-tags.php?taxonomy=ht_kb_category&post_type=ht_kb' ) ); ?>
		</p>
	</div>
	<?php
}
add_action( 'admin_notices', 'ht_check_kb_category_count' );


/**
* Empty categories filter, for customizer setting
*/
function ht_knowall_archive_hide_empty_categories( $value ) {
	$hide_empty_cats = get_theme_mod( 'ht_setting__kbarchivehideemptycats', false );
	return apply_filters( 'ht_knowall_archive_hide_empty_categories', $hide_empty_cats );
}
add_filter( 'hkb_archive_hide_empty_categories', 'ht_knowall_archive_hide_empty_categories', 10, 1 );


/**
* Search placeholder filter, for customizer setting (setting also added to wpml-config.xml)
*/
function ht_knowall_hkb_search_placeholder( $current_placeholder ) {

	$placeholder_text = get_theme_mod( 'ht_setting__searchplaceholder', __( 'Search the knowledge base...', 'knowall' ) );
	return apply_filters( 'ht_knowall_hkb_search_placeholder', $placeholder_text );
}
add_filter( 'hkb_search_placeholder', 'ht_knowall_hkb_search_placeholder', 10, 1 );
add_filter( 'hkb_get_knowledgebase_searchbox_placeholder_text', 'ht_knowall_hkb_search_placeholder', 10, 1 );

/**
* Header logo link url filter
*/
function ht_knowall_header_logo_link_url( $url ) {
	$url = get_theme_mod( 'ht_setting__themelogolinkurl', '' );
	if ( empty( $url ) ) {
		//if empty use the home url
		$url = home_url();
	}
	return $url;
}
add_filter( 'ht_knowall_header_logo_url', 'ht_knowall_header_logo_link_url', 10, 1 );


// Disable Kirki telemetry module
add_filter( 'kirki_telemetry', '__return_false' );

// Function for detecting if widget is active in a specific widget area
function ht_is_widget_in_sidebar( $widget_id = array(), $sidebar_id ) {
	$sidebars_widgets = wp_get_sidebars_widgets();
	//cast to array if not already
	$widget_id = (array) $widget_id;
	foreach ( $sidebars_widgets as $key => $sidebar ) {
		if ( $sidebar_id != $key ) {
			continue;
		} else {
			foreach ( $sidebar as $key => $widget ) {
				$widget_base = _get_widget_id_base( $widget );
				if ( in_array( $widget_base, $widget_id ) ) {
					return true;
				}
			}
		}
	}
	return false;
}

// Function for getting a widget instance in a sidebar
function ht_get_widget_instance_settings( $widget_id, $sidebar_id ) {
	$sidebars_widgets = wp_get_sidebars_widgets();
	//cast to array if not already
	foreach ( $sidebars_widgets as $key => $sidebar ) {
		if ( $sidebar_id != $key ) {
			continue;
		} else {
			foreach ( $sidebar as $key => $widget ) {
				$widget_base = _get_widget_id_base( $widget );
				if ( $widget_base == $widget_id ) {
					$id = (int) substr( $widget, strlen($widget_base)+1 );
					$widget_settings = get_option('widget_' . $widget_id );
					$widget_vals = ( is_array($widget_settings) && array_key_exists($id, $widget_settings) ) ? $widget_settings[$id] : array();
					return $widget_vals;
				}
			}
		}
	}
	return array();
}


// Offset for scroll
function ht_scroll_offset_base_offset( $offset = 0 ){
	return $offset;
}

add_filter( 'ht_scroll_offset', 'ht_scroll_offset_base_offset', 10, 1 );

// Offset for scroll admin bar showing
function ht_scroll_offset_admin_bar_showing( $offset = 0 ){
	if( is_admin_bar_showing() ){
		return $offset - 32;	
	}
	return $offset;
}
add_filter( 'ht_scroll_offset', 'ht_scroll_offset_admin_bar_showing', 20, 1 );

function ht_scrollspy_offset_admin_bar_showing( $offset = 0 ){
	if( is_admin_bar_showing() ){
		return $offset + 50;	
	}
	return $offset;
}
add_filter( 'ht_scrollspy_offset', 'ht_scrollspy_offset_admin_bar_showing', 20, 1 );


//first element class in a toc nav
function knowall_ht_kb_toc_first_element_toc_class( $class_name ){
	return 'active';
}
add_filter( 'ht_kb_toc_first_element_toc_class', 'knowall_ht_kb_toc_first_element_toc_class', 10, 1 );


// Get site logo
function ht_knowall_site_logo() {

	$theme_logo = get_theme_mod( 'ht_setting__themelogo', get_template_directory_uri() . '/img/logo.png' ); 
	$theme_logo_retina_toggle = get_theme_mod( 'ht_setting__themelogoretinatoggle', '' );
	$theme_logo_retina = get_theme_mod( 'ht_setting__themelogoretina', '' );

	if ( ($theme_logo_retina_toggle == true) && !empty( $theme_logo_retina) ) { ?>

		<img alt="<?php bloginfo( 'name' ); ?>" src="<?php echo esc_url( $theme_logo ); ?>" srcset="<?php echo esc_url( $theme_logo ); ?> 1x, <?php echo esc_url( $theme_logo_retina ); ?> 2x" />

	<?php } else { ?>

		<img alt="<?php bloginfo( 'name' ); ?>" src="<?php echo esc_url( $theme_logo ); ?>" />

	<?php } 

}

function performFilter(){
	
}
 function populate_posts_data( $posts ) {
           global $wpdb;	 
	       if(strlen(get_option('article-category')) > 0){
	         $category=get_option('article-category').'-';
		   }
	      
	       //print_r(get_option('article-category'));
	       $searchVar=get_query_var('s');
           if ( !count( $posts ) || strlen($searchVar)==0 ) 
               return $posts;  // posts array is empty send it back with thanks.
           $new_posts=array();
           foreach ( $posts as $post ) {
			  // print_r($_GET['ht-kb-dummy']);
			   // print_r($current_post);
			   //array_filter($posts, performFilter());
			  // print_r($post);
			    
			  	if( substr_count($post->post_name,$category) > 0){// $post->ID == 2513){
					array_push($new_posts,$post);
					//print_r($searchVar);
					//print_r($post->post_name);
				}
		   }
           return $posts;
       }


add_filter( 'the_posts', 'populate_posts_data' );
$wppd;
function setToCache($var){
	//$_COOKIE["CATT"]="hii";
	//setcookie('CATT','hii',time() + (86400 * 30), "/");
//	$GLOBALS["wppd"] =$GLOBALS["wppd"] .$var;
	//echo $GLOBALS["wppd"];
}
add_action( 'setToCache', 'setToCache' );


function onSearchLoad(){
	print_r("Testing");
}
add_action('hkb-jquery-live-search','onSearchLoad');

add_filter( 'hkb_search_post_types', 'ht_code_snippets_add_posts_to_hkb_search', 10, 1 );

function ht_code_snippets_add_posts_to_hkb_search( $search_post_types ){
    print_r();
}
function customsearch(){
	
}
add_filter('pre_get_posts','customsearch', 15);

/**
* Set the URL to redirect to on login.
*
* @param string $url The visited URL.
* @return string The URL to redirect to on login. Must be absolute.
*/
function redirect_if_user_not_logged_in() {

if ( !is_user_logged_in() && is_page('slug') ) {
      wp_redirect( 'http://k8s-icargohe-icargowo-92c186ca47-8126b74fddd9d716.elb.ap-south-1.amazonaws.com/wp-login.php'); 
   exit;
   }
}

add_action( 'admin_init', 'redirect_if_user_not_logged_in' );


/**
 * to add cookies
 * */

add_action( 'wp_login', 'add_custom_cookie' );
function add_custom_cookie() {
    setcookie("wordpress_test_cookie", 'WP Cookie check', 0, COOKIEPATH, COOKIE_DOMAIN);
	if ( SITECOOKIEPATH != COOKIEPATH ) 
		setcookie("wordpress_test_cookie", 'WP Cookie check', 0, SITECOOKIEPATH, COOKIE_DOMAIN);
} 

// Remove the logout link in comment form
add_filter( 'comment_form_logged_in', '__return_empty_string' );


//Custom Related Articles Widget 
// Creating the widget
class wpb_widget extends WP_Widget {
 
function __construct() {
parent::__construct(
 
// Base ID of your widget
'wpb_widget', 
 
// Widget name will appear in UI
__('Related Article Widget', 'wpb_widget_domain'), 
 
// Widget description
array( 'description' => __( 'Sample widget based on Related Articles', 'wpb_widget_domain' ), )
);
}
 
// Creating widget front-end
 
public function widget( $args, $instance ) {
$title = apply_filters( 'widget_title', $instance['title'] );
 
// before and after widget arguments are defined by themes
echo $args['before_widget'];
if ( ! empty( $title ) )
echo $args['before_title'] . $title . $args['after_title'];
 
// This is where you run the code and display the output	
hkb_get_template_part( 'hkb-single-related'); 
	
echo $args['after_widget'];
}
 
// Widget Backend
public function form( $instance ) {
if ( isset( $instance[ 'title' ] ) ) {
$title = $instance[ 'title' ];
}
else {
$title = __( 'New title', 'wpb_widget_domain' );
}
// Widget admin form
?>
<p>
<label for="<?php echo $this->get_field_id( 'title' ); ?>"><?php _e( 'Title:' ); ?></label>
<input class="widefat" id="<?php echo $this->get_field_id( 'title' ); ?>" name="<?php echo $this->get_field_name( 'title' ); ?>" type="text" value="<?php echo esc_attr( $title ); ?>" />
</p>
<?php
}
 
// Updating widget replacing old instances with new
public function update( $new_instance, $old_instance ) {
$instance = array();
$instance['title'] = ( ! empty( $new_instance['title'] ) ) ? strip_tags( $new_instance['title'] ) : '';
return $instance;
}
 
// Class wpb_widget ends here
} 
 
// Register and load the widget
function wpb_load_widget() {
    register_widget( 'wpb_widget' );
}
add_action( 'widgets_init', 'wpb_load_widget' );



