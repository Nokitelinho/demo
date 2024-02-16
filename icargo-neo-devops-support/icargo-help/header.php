<!DOCTYPE html>
<?php
		session_start();
?>
<html <?php language_attributes(); ?>>
	<head>
		<meta http-equiv="x-ua-compatible" content="ie=edge">
		<meta charset="<?php bloginfo( 'charset' ); ?>" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="profile" href="http://gmpg.org/xfn/11" />
		<link rel="preconnect" href="https://fonts.googleapis.com"/>
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin/>
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;400;500;700&display=swap" rel="stylesheet"/>
		<?php wp_head(); ?>
	</head>
<?php $ht_setting__sitelayout = get_theme_mod( 'ht_setting__sitelayout', 'wide' ); ?>
<?php $ht_searchtitle = get_theme_mod( 'ht_setting__searchtitle', __( 'How can we help?', 'knowall' ) ); ?>
<body <?php body_class(); ?> itemscope itemtype="https://schema.org/WebPage" <?php ht_output_body_attributes(); ?>>
<?php wp_body_open(); ?>
<div class="ht-sitecontainer ht-sitecontainer--<?php echo esc_attr( $ht_setting__sitelayout ); ?>">

<!-- .site-header -->
<div class="site-header">

	<header class="site-header__banner" itemscope itemtype="http://schema.org/WPHeader">
	<div class="ht-container">
// Getting the tenant from the request uri and setting the same in Session
<?php
		$current_url = $_SERVER["REQUEST_URI"];
		if (strpos($current_url, "cmpcod") > 0){
			$result = $_GET['cmpcod'];
			$_SESSION["tenant"]=$result;
		}
?>
		<!-- .site-logo -->
		<div class="site-logo" name="site-logo" id="site-logo">
			<a href="<?php echo esc_url( apply_filters( 'ht_knowall_header_logo_url', home_url() ) ); ?>" data-ht-sitetitle="<?php bloginfo( 'name' ); ?>">
				<?php $theme_logo = get_theme_mod( 'ht_setting__themelogo', get_template_directory_uri() . '/img/logo.png' ); ?>
				<?php if ( '' != $theme_logo ) : ?>
					<?php echo ht_knowall_site_logo(); ?>
				<?php endif; ?>
				<?php if ( is_front_page() ) : ?>
					<h1 class="site-logo__title" itemprop="headline"><?php bloginfo( 'name' ); ?></h1>
				<?php else : ?>
					<meta itemprop="headline" content="<?php bloginfo( 'name' ); ?>">
				<?php endif; ?>
			</a>
		</div>
		<!-- /.site-logo -->

		<?php if ( has_nav_menu( 'nav-site-header' ) ) : ?>
			<!-- .nav-header -->
			<nav class="nav-header" itemscope itemtype="https://schema.org/SiteNavigationElement">
				<button id="ht-navtoggle" class="nav-header__mtoggle"><span><?php echo esc_html_e( 'Menu', 'knowall' ); ?></span></button>
				<?php
				wp_nav_menu(
					array(
						'theme_location'  => 'nav-site-header',
						'menu_class'      => 'nav-header__menuwrapper',
						'container'       => false,
						'container_id'    => false,
						'container_class' => false,
						'depth'           => 2,
					)
				);
				?>
			</nav>
			<!-- /.nav-header -->
		<?php endif; ?>

	</div>
	</header>
	<!-- /.site-header -->

	<?php if ( is_front_page() || is_post_type_archive( 'ht_kb' ) || ( function_exists( 'ht_kb_is_ht_kb_front_page' ) && ht_kb_is_ht_kb_front_page() ) ) : ?>
		<!-- .site-header__search-->
		<div class="site-header__search">
		<div class="ht-container">

			<?php if ( '' != $ht_searchtitle ) : ?>
				<h2 class="site-header__title"><?php echo esc_html( $ht_searchtitle ); ?></h2>
			<?php endif; ?>

			<?php if ( function_exists( 'hkb_get_template_part' ) ) : ?>
				<?php hkb_get_template_part( 'hkb-searchbox' ); ?>
			<?php endif; ?>
		</div>
		</div>
		<!-- /.site-header__search -->
	<?php elseif ( is_home() || is_category() || is_tag() ) : ?>
		<?php
		$ht_page_for_posts = get_option( 'page_for_posts' );
		$ht_page_for_posts = get_the_title( $ht_page_for_posts );
		?>
		<div class="site-header__posts">
		<div class="ht-container">
			<h1 class="site-header__title"><?php echo esc_html( $ht_page_for_posts ); ?></h1>
		</div>
		</div>
	<?php endif; ?>

	<?php if ( is_404() ) : ?>
		<div class="ht-404msg">
		<div class="ht-container">
			<h1 class="ht-404msg__title"><?php esc_html_e( '404', 'knowall' ); ?></h1>
			<h2 class="ht-404msg__tagline"><?php esc_html_e( 'Page not found.', 'knowall' ); ?></h2>
		</div>
		</div> 
	<?php endif; ?>


</div>
<!-- /.site-header -->
