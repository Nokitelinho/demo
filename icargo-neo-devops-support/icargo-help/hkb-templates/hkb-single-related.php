<?php
/*
*
* The template used for displaying related articles
*
*/ ?>

<?php $related_articles = hkb_get_related_articles();
	$terms = wp_get_post_terms( $post->ID, 'ht_kb_category' );
	$args = array(
				'post_type' => 'ht_kb',
				'posts_per_page' => -1,
				'tax_query' => array(
						array(
							'taxonomy' => 'ht_kb_category',
							'field' => 'term_id',
							'terms' => $terms[0]->term_id
							)
						)
				 );
	$ht_kb_posts = get_posts( $args );
?>

<?php if ( ! empty( $related_articles ) && $related_articles->have_posts() ) : ?>
<!-- .hkb-article__related -->     
	<section class="hkb-article-related">
		<h3 class="hkb-article-related__title"><?php esc_html_e( 'Related Articles', 'knowall' ); ?></h3>
		<ul>
		<?php			
			$post_slug = $post->post_name;
			$ParentArticleSlug;
			$post_tag_list = get_the_term_list(  $post->ID, 'ht_kb_tag', '', '', '' );

			foreach($ht_kb_posts as $all_cat_post){	?>
				<?php 
					$article_slug = $all_cat_post->post_name;
					$article_tag_list = get_the_term_list(  $all_cat_post->ID, 'ht_kb_tag', '', '', '' );
 				?>
				<?php 
					if (strpos($article_tag_list, $post_slug) > 0 ) { ?>	
				<li class="hkb-article-list__<?php hkb_post_format_class(); ?>">
						<a href="<?php the_permalink($all_cat_post->ID); ?>"><?php echo $all_cat_post->post_title ?></a>
				</li>
						
				<?php } elseif(strpos($post_tag_list, $article_slug) > 0 ) { ?>
						<li class="hkb-article-list__<?php hkb_post_format_class(); ?>">
						<a href="<?php the_permalink($all_cat_post->ID); ?>"><?php echo $all_cat_post->post_title ?></a>
				</li>
			<?php 
					if (strpos($article_tag_list, 'ParentTag') > 0) { 
						$ParentArticleSlug=$article_slug;
					}?>	
	
				<?php } ?>
		<?php } ?>
<!-- 			 -->
			<?php 
				foreach($ht_kb_posts as $all_cat_post){	
			?>
				<?php 
					$article_slug = $all_cat_post->post_name;
					$article_tags = get_the_term_list(  $all_cat_post->ID, 'ht_kb_tag', '', '', '' );
			?>
				<?php 
					if (strpos($article_tags, $ParentArticleSlug) > 0 && $post_slug !==$article_slug) { 
			?>	
				<li class="hkb-article-list__<?php hkb_post_format_class(); ?>">
						<a href="<?php the_permalink($all_cat_post->ID); ?>"><?php echo $all_cat_post->post_title ?></a>
				</li>
				<?php } ?>
		<?php } ?>
			
		</ul>
	</section>
<!-- /.hkb-article__related -->
<?php endif; ?>

<?php
	//important - reset the post
	hkb_after_releated_post_reset();
?>
