/*
* Getting the tenant from Session
*/
<?php 
	session_start(); 
	$cmpcod = $_SESSION['tenant'];
?>
<?php $subcategories = hkb_get_subcategories(); ?>
<?php if ( $subcategories ) : ?>

	<!--.hkb-subcats-->
	<ul class="hkb-subcats <?php echo esc_attr( ht_kbarchive_style() ); ?>">
		<?php foreach ( $subcategories as $subcategory ) : ?>
		// Filtering the categories containing the tenant in their slug
		<?php if (strpos($subcategory->slug, $cmpcod) > 0) { ?>
			<li>
				<?php
					$hkb_current_term_id    = $subcategory->term_id;
					$hkb_current_term_class = apply_filters( 'hkb_current_term_class_prefix', 'hkb-category--', 'subcategories' ) . $hkb_current_term_id;
					$hkb_current_term_class = apply_filters( 'hkb_current_term_class', $hkb_current_term_class, $hkb_current_term_id );
				?>
				<div class="hkb-category hkb-category-custom-height <?php echo esc_attr( ht_kbarchive_catstyle( $hkb_current_term_id ) ); ?> <?php echo esc_attr( $hkb_current_term_class ); ?>">				
				// appending the tenant code with the article url
					<?php $custom_sub_cat_termlink=esc_attr( get_term_link( $subcategory, 'ht_kb_category' ) ); ?>
					<?php $custom_sub_cat_termlink .= "?cmpcod=$cmpcod"; ?>
					<a class="hkb-category__link hkb-category__link--nopad" href="<?php echo $custom_sub_cat_termlink ?>">
						<?php if ( hkb_has_category_custom_icon( $subcategory->term_id ) == 'true' ) : ?>
							<div class="hkb-category__iconwrap"><?php hkb_category_thumb_img( $subcategory->term_id ); ?></div>
						<?php endif; ?>
						<div class="hkb-category__content">
							<h2 class="hkb-category__title">
								<?php echo esc_html( $subcategory->name ); ?>
							</h2>
<!-- false added to prevent description in screen-->
							<?php if (false && ( '' != $subcategory->description ) && get_theme_mod( 'ht_setting__kbarchivecatdesc', '1' ) == true ) : ?>
								<div class="hkb-category__description">
									<?php echo esc_html( $subcategory->description ); ?>
								</div>
							<?php endif; ?>                            
						</div>
					</a>
					<!-- sub categories -->
                    <?php $subbcategories = hkb_get_subcategories($hkb_current_term_id); ?>
                            <?php if ( $subbcategories ) : ?>
					<ul class="hkb-category__ul" >
							<?php $n_subbcategory = 0; ?>
							<?php foreach ( $subbcategories as $subbcategory ) : ?> 
							<?php $n_subbcategory++ ?>
								<?php if ( get_theme_mod( 'ht_knowall_hkb_archivearticles_num', $n_subbcategory ) == 4) : ?>
								<?php break ; ?>
								<?php endif; ?>	
						<?php if (strpos($subbcategory->slug, $cmpcod) > 0) { ?>
						<li>
							<?php $custom_subbcat_termlink=esc_attr( get_term_link( $subbcategory, 'ht_kb_category' ) ); ?>
							<?php $custom_subbcat_termlink .= "?cmpcod=$cmpcod"; ?>
							  <a class="" href="<?php echo $custom_subbcat_termlink; ?>"><?php echo esc_html( $subbcategory->name ); ?></a>
						</li>
						<?php } ?>
						<?php $articles = hkb_get_archive_articles($subbcategories -> term_id); ?>
						<?php count($articles) ;?>
							<?php endforeach; ?>
						<a class="hkb-category__viewall" href="<?php echo esc_attr(get_term_link($hkb_current_term_id, 'ht_kb_category')) ?>"><?php _e( 'View all →', 'knowall' ); ?></a>
					</ul>
					<?php endif; ?>  
					<!-- sub categories -->
					<!-- articles -->
					<?php $cat_posts = hkb_get_archive_articles( $subcategory, null, null, 'kb_home' );?>
					<?php if ( $cat_posts ) : ?>
					<ul class="hkb-category__ul">
						<?php $n_articlecount = 0; ?>
										<?php foreach ( $cat_posts as $cat_post ) : ?>
						                   <?php $tag_list = get_the_term_list( $cat_post->ID, 'ht_kb_tag', '', '', '' ); ?>
						                  <?php if(!(strpos($tag_list, 'Tenant') > 0 && strpos($tag_list, 'Tenant:LH') == 0 )) : ?>
						                    <?php $n_articlecount++ ?>
						                    <?php if ( get_theme_mod( 'ht_knowall_hkb_archivearticles_num', $n_articlecount ) == 4) : ?>
								<?php break ; ?>
								<?php endif; ?>	
											<li>
												<a href="<?php echo esc_url( get_permalink( $cat_post->ID ) ); ?>"><?php                                                  echo esc_html( get_the_title( $cat_post->ID ) ); ?></a>
											</li>
						<?php endif; ?>
										<?php endforeach; ?>
						               <a class="hkb-category__viewall" href="<?php echo esc_attr(get_term_link($hkb_current_term_id, 'ht_kb_category')) ?>"><?php _e( 'View all →', 'knowall' ); ?></a>
									</ul>
					<?php endif; ?>
					<!-- articles -->
				</div>
			</li>
		<?php } ?>
		<?php if(empty($cmpcod)) { ?>
			<li>
				<?php
					$hkb_current_term_id    = $subcategory->term_id;
					$hkb_current_term_class = apply_filters( 'hkb_current_term_class_prefix', 'hkb-category--', 'subcategories' ) . $hkb_current_term_id;
					$hkb_current_term_class = apply_filters( 'hkb_current_term_class', $hkb_current_term_class, $hkb_current_term_id );
				?>
				<div class="hkb-category hkb-category-custom-height <?php echo esc_attr( ht_kbarchive_catstyle( $hkb_current_term_id ) ); ?> <?php echo esc_attr( $hkb_current_term_class ); ?>">

					<a class="hkb-category__link hkb-category__link--nopad" href="<?php echo esc_attr( get_term_link( $subcategory, 'ht_kb_category' ) ); ?>">

						<?php if ( hkb_has_category_custom_icon( $subcategory->term_id ) == 'true' ) : ?>
							<div class="hkb-category__iconwrap"><?php hkb_category_thumb_img( $subcategory->term_id ); ?></div>
						<?php endif; ?>

						<div class="hkb-category__content">

							<h2 class="hkb-category__title">
								<?php echo esc_html( $subcategory->name ); ?>
							</h2>
<!-- false added to prevent description in screen-->
							<?php if (false && ( '' != $subcategory->description ) && get_theme_mod( 'ht_setting__kbarchivecatdesc', '1' ) == true ) : ?>
								<div class="hkb-category__description">
									<?php echo esc_html( $subcategory->description ); ?>
								</div>
							<?php endif; ?>                            
                            
						</div>

					</a>
					
					<!-- sub categories -->
                    <?php $subbcategories = hkb_get_subcategories($hkb_current_term_id); ?>
                            <?php if ( $subbcategories ) : ?>
					<ul class="hkb-category__ul" >
						
							<?php $n_subbcategory = 0; ?>
							<?php foreach ( $subbcategories as $subbcategory ) : ?> 
							<?php $n_subbcategory++ ?>
						
							
								<?php if ( get_theme_mod( 'ht_knowall_hkb_archivearticles_num', $n_subbcategory ) == 4) : ?>
							
								<?php break ; ?>
								<?php endif; ?>	
						<li>
							  <a class="" href="<?php echo esc_attr( get_term_link( $subbcategory, 'ht_kb_category' ) ); ?>"><?php echo esc_html( $subbcategory->name ); ?></a>
							
						</li>
						<?php $articles = hkb_get_archive_articles($subbcategories -> term_id); ?>
						<?php count($articles) ;?>
							<?php endforeach; ?>
						<a class="hkb-category__viewall" href="<?php echo esc_attr(get_term_link($hkb_current_term_id, 'ht_kb_category')) ?>"><?php _e( 'View all →', 'knowall' ); ?></a>
							
					</ul>
					
					<?php endif; ?>  
					<!-- sub categories -->
					
					<!-- articles -->
					<?php $cat_posts = hkb_get_archive_articles( $subcategory, null, null, 'kb_home' );?>
					<?php if ( $cat_posts ) : ?>
					<ul class="hkb-category__ul">
						<?php $n_articlecount = 0; ?>
										<?php foreach ( $cat_posts as $cat_post ) : ?> 
						                    <?php $n_articlecount++ ?>
						                    <?php if ( get_theme_mod( 'ht_knowall_hkb_archivearticles_num', $n_articlecount ) == 4) : ?>
							
								<?php break ; ?>
								<?php endif; ?>	
											<li>
												<a href="<?php echo esc_url( get_permalink( $cat_post->ID ) ); ?>"><?php                                                  echo esc_html( get_the_title( $cat_post->ID ) ); ?></a>
											</li>
										<?php endforeach; ?>
						               <a class="hkb-category__viewall" href="<?php echo esc_attr(get_term_link($hkb_current_term_id, 'ht_kb_category')) ?>"><?php _e( 'View all →', 'knowall' ); ?></a>
									</ul>
					<?php endif; ?>
					<!-- articles -->
				</div>
				
			</li>
		<?php endforeach; ?>
	</ul>
	<!--/.hkb-subcats-->

<?php endif; ?>
