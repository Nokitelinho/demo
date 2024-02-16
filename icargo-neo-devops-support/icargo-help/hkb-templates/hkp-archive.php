<?php get_header(); ?>

<!-- .ht-page -->
<div class="ht-page <?php ht_sidebarpostion_homepage(); ?>">
<div class="ht-container">
// Getting the tenant from Session
<?php
	session_start();
	$cmpcod = $_SESSION['tenant'];
?>

	<?php ht_get_sidebar_homepage( 'left' ); ?>

	<div class="ht-page__content">

	<?php
	$ht_hometitle = get_theme_mod( 'ht_setting__homepagetitle', __( 'Help Topics', 'knowall' ) );
	if ( '' != $ht_hometitle ) :
		?>
		<h2 class="hkb-archive__title"><?php echo esc_html( $ht_hometitle ); ?></h2>
	<?php endif; ?>

		<?php global $hkb_current_term_id, $tax_term; ?>
		<?php $tax_terms = hkb_get_archive_tax_terms(); ?>

		<!-- .hkb-archive -->
		<?php if ( $tax_terms ) : ?>

			<ul class="hkb-archive <?php echo esc_attr( ht_kbarchive_style() ); ?>">
				<?php foreach ( $tax_terms as $key => $tax_term ) : ?>
					<?php
					//set hkb_current_term_id
					$hkb_current_term_id    = $tax_term->term_id;
					$hkb_current_term_class = apply_filters( 'hkb_current_term_class_prefix', 'hkb-category--', 'archive' ) . $hkb_current_term_id;
					$hkb_current_term_class = apply_filters( 'hkb_current_term_class', $hkb_current_term_class, $hkb_current_term_id );
					?>
				<li>

					<div class="hkb-category <?php echo esc_attr( ht_kbarchive_catstyle( $hkb_current_term_id ) ); ?> <?php echo esc_attr( $hkb_current_term_class ); ?>">

					<?php if ( get_theme_mod( 'ht_setting__kbarchivecatarticles', '0' ) != true ) : ?>
					<a class="hkb-category__link" href="<?php echo esc_attr( get_term_link( $tax_term, 'ht_kb_category' ) ); ?>">
					<?php endif; ?>

						<?php if ( hkb_has_category_custom_icon( $hkb_current_term_id ) == 'true' ) : ?>
							<div class="hkb-category__iconwrap"><?php hkb_category_thumb_img( $hkb_current_term_id ); ?></div>
						<?php endif; ?>

						<div class="hkb-category__content">
							<?php if ( get_theme_mod( 'ht_setting__kbarchivecatarticles', '0' ) == true ) : ?>
							// appending the tenant code with the category url
							<?php $custom_termlink=esc_attr( get_term_link( $tax_term, 'ht_kb_category' ) ); ?>
							<?php $custom_termlink .= "?cmpcod=$cmpcod"; ?>
								<a class="hkb-category__headerlink" href="<?php echo $custom_termlink; ?>">
							<?php endif; ?>

							<h2 class="hkb-category__title">
								<?php echo esc_html( $tax_term->name ); ?>
							</h2>

							<?php if ( ( '' != $tax_term->description ) && get_theme_mod( 'ht_setting__kbarchivecatdesc', '1' ) == true ) : ?>
								<div class="hkb-category__description"><?php echo esc_html( $tax_term->description ); ?></div>
							<?php endif; ?>

							<?php if ( get_theme_mod( 'ht_setting__kbarchivecatarticles', '0' ) == true ) : ?>
								</a>
							<?php endif; ?>
							<!-- subcategory -->
                           <?php $subcategories = hkb_get_subcategories($hkb_current_term_id); ?>
                           <?php if ( $subcategories ) : ?>
							<?php $n_subcategory = 0; ?>
							<ul class="hkb-category__ul" >
							<?php foreach ( $subcategories as $subcategory ) : ?> 
							// Filtering the subcategories containing the tenant in their slug
							<?php 
							if (strpos($subcategory->slug, $cmpcod) > 0) { ?>
							<li>
								<?php $n_subcategory++ ?>
								<?php if ( get_theme_mod( 'ht_knowall_hkb_archivearticles_num', $n_subcategory ) == 4) : ?>
								<?php break ; ?>
								<?php endif; ?>		
								// appending the tenant code with the subcategory url
								<?php $custom_sub_cat_termlink=esc_attr( get_term_link( $subcategory, 'ht_kb_category' ) ); ?>
								<?php $custom_sub_cat_termlink .= "?cmpcod=$cmpcod"; ?>
								<a class="" href="<?php echo $custom_sub_cat_termlink ?>"><?php echo esc_html( $subcategory->name ); ?></a>							
							</li>
								<?php } ?>
							<?php if(empty($cmpcod)) { ?>
								<li>						 									 
								<?php $n_subcategory++ ?>
								<?php if ( get_theme_mod( 'ht_knowall_hkb_archivearticles_num', $n_subcategory ) == 4) : ?>
							
								<?php break ; ?>
								<?php endif; ?>								  
								<a class="" href="<?php echo esc_attr( get_term_link( $subcategory, 'ht_kb_category' ) ); ?>"><?php echo esc_html( $subcategory->name ); ?></a>							
							</li>
								<?php } ?>
							<?php endforeach; ?>
								
								<a class="hkb-category__viewall" href="<?php echo esc_attr(get_term_link($tax_term, 'ht_kb_category')) ?>"><?php _e( 'View all →', 'knowall' ); ?></a>
							</ul>	
							<?php endif; ?>
							<!-- subcategory -->
							<?php
							if ( get_theme_mod( 'ht_setting__kbarchivecatarticles', '0' ) == true ) :
								$cat_posts = hkb_get_archive_articles( $tax_term, null, null, 'kb_home' );
								?>
								<?php if ( ! empty( $cat_posts ) && ! is_a( $cat_posts, 'WP_Error' ) ) : ?>

									<ul class="hkb-category__ul">
										<?php foreach ( $cat_posts as $cat_post ) : ?>                            
											<li>
												<a href="<?php echo esc_url( get_permalink( $cat_post->ID ) ); ?>"><?php echo esc_html( get_the_title( $cat_post->ID ) ); ?></a>
											</li>
										<?php endforeach; ?>
										<?php if ( get_theme_mod( 'ht_setting__kbarchivecatarticles_viewall', '0' ) == true ) : ?>
										<a class="hkb-category__viewall" href="<?php echo esc_attr(get_term_link($tax_term, 'ht_kb_category')) ?>"><?php _e( 'View all →', 'knowall' ); ?></a>
									<?php endif; ?>
									</ul>
									
								   
								<?php endif; ?>
							<?php endif; ?>

						</div>

					<?php if ( get_theme_mod( 'ht_setting__kbarchivecatarticles', '0' ) != true ) : ?> 
						</a>
					<?php endif; ?>
					</div>                 

				</li>
				<?php endforeach; ?>
			</ul> 

		<?php else : ?>

			<div class="hkb-no-categories"><?php esc_html_e( 'No knowledge base categories to display', 'knowall' ); ?></div>

		<?php endif; ?>
		<!-- /.hkb-archive -->

		<?php
			// If HKB Exit widget is active, display mobile version on appropriate screen sizes
		if ( ht_is_widget_in_sidebar( 'ht-kb-exit-widget', 'sidebar-home' ) ) :

			$widget_instance = ht_get_widget_instance_settings( 'ht-kb-exit-widget', 'sidebar-home' );

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

	<?php ht_get_sidebar_homepage( 'right' ); ?>

</div>
</div>
<!-- /.ht-page -->

<?php
get_footer();
