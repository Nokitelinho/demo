<?php
/*
*
* Theme template for ht_kb_category
*
*/?>

<?php get_header(); ?>
<!-- added the below version check as a part of version management, for getting the version ID from the URI -->
<?php
	$current_url = $_SERVER["REQUEST_URI"];
	$version = $_GET['version'];
	$screenID = $_GET['screenID'];
?>

<?php hkb_get_template_part( 'hkb-pageheader', 'single' ); ?>
<!-- .ht-page -->
<div class="ht-page <?php ht_sidebarpostion_kbcategory(); ?>">
<div class="ht-container">

	<?php ht_get_sidebar_kbcategory( 'left' ); ?>

	<div class="ht-page__content">
		<?php
			$hkb_current_term_id    = get_queried_object()->term_id;
			$hkb_current_term_class = apply_filters( 'hkb_current_term_class_prefix', 'hkb-category--', 'taxonomy-category' ) . $hkb_current_term_id;
			$hkb_current_term_class = apply_filters( 'hkb_current_term_class', $hkb_current_term_class, $hkb_current_term_id );
		?>
		
		<div class="ht-categoryheader <?php echo esc_attr( ht_kbarchive_catstyle( $hkb_current_term_id ) ); ?> <?php echo esc_attr( $hkb_current_term_class ); ?>">
			<?php if ( hkb_has_category_custom_icon( get_queried_object()->term_id ) == 'true' ) : ?>
				<div class="hkb-category__iconwrap"><?php hkb_category_thumb_img( get_queried_object()->term_id ); ?></div>
			<?php endif; ?>
			<div class="ht-categoryheader__content">
				<h1 class="ht-categoryheader__title">
					<?php hkb_term_name(); ?>
				</h1>
				<?php if ( ( get_queried_object()->description != '' ) && get_theme_mod( 'ht_setting__kbarchivecatdesc', '1' ) == true ) : ?>
					<div class="ht-categoryheader__description">
						<?php echo esc_html( get_queried_object()->description ); ?>
					</div>
				<?php endif; ?>
				
			</div>
		</div>
        <?php $all_cat_posts = get_posts('post_type=ht_kb');?>
		<?php hkb_get_template_part( 'hkb-subcategories' ); ?>

		<?php if ( have_posts() ) : ?>

		<ul class="ht-articlelist">
			<?php
			while ( have_posts() ) :
				the_post();
				?>
			<?php $tag_list = get_the_term_list( $post->ID, 'ht_kb_tag', '', '', '' ); 
<!-- added the below version check as a part of version management, we can restrict the article based on the version given in the tag of each article -->
 		if (strpos($tag_listss, $version) > 0) { ?>
<!-- added the below screenID check to filter the parent articles based on the screenID given in article tag, -->
		<?php if (strpos($tag_listss, $screenID) > 0 ){ 
			if (strpos($tag_listss, 'ParentTag') > 0 ){?> 
			<li><?php hkb_get_template_part( 'hkb-content-article', 'category', $args); ?></li>
			<?php }?>
 			
			<?php }  
            elseif(empty($screenID) > 0) {?>
            <?php if (strpos($tag_listss, 'ParentTag') > 0 ){ ?> 
				<?php $post_slug = $post->post_name; ?>
				<li class="parent">
					<?php 			
				hkb_get_template_part( 'hkb-content-article', 'category', $args); 
				foreach($all_cat_posts as $all_cat_post){
					$childlist = get_the_term_list(  $all_cat_post->ID, 'ht_kb_tag', '', '', '' ); 
					if (strpos($childlist, $post_slug) > 0) {
						the_post();
						hkb_get_template_part( 'hkb-content-article', 'category', $args); 
					}			
				}				
				?>	
				</li>
            <?php } else{ ?>
			<li>
			<?php hkb_get_template_part( 'hkb-content-article', 'category', $args);  ?>
			</li>
			<?php } } }?>
			
			<?php endwhile; ?>
		</ul>

			<?php ht_posts_nav_link(); ?>

		<?php else : ?>

			<?php $subcategories = hkb_get_subcategories( hkb_get_term_id() ); ?>
			<?php if ( ! $subcategories ) : ?>
				<p><?php esc_html_e( 'No articles in this category.', 'knowall' ); ?></p>
			<?php endif; ?>

		<?php endif; ?>

		<?php
			// If HKB Exit widget is active, display mobile version on appropriate screen sizes
		if ( ht_is_widget_in_sidebar( 'ht-kb-exit-widget', 'sidebar-category' ) ) :

			$widget_instance = ht_get_widget_instance_settings( 'ht-kb-exit-widget', 'sidebar-category' );

			$ht_mobile_exit_args = array(
				'before_widget' => '<div class="ht-mobile-exit">',
				'after_widget'  => '</div>',
				'before_title'  => '<strong class="ht-mobile-exit__title">',
				'after_title'   => '</strong>',
			);
			the_widget( 'HT_KB_Exit_Widget', $widget_instance, $ht_mobile_exit_args );
		?>
			<?php endif; ?>

	</div>

	<?php ht_get_sidebar_kbcategory( 'right' ); ?>

</div>
</div>
<!-- /.ht-page -->

<?php
get_footer();
