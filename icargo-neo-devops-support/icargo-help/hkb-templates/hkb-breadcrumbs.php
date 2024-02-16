<?php
/*
*
* Breadcrumbs template
*
*/
?>
/*
* Getting the tenant from Session
*/

<?php 
	$cmpcod = $_SESSION['tenant'];
?>
<!-- .hkb-breadcrumbs -->
<div class="hkb-breadcrumbs_wrap">
<?php $breadcrumbs_paths = ht_kb_get_ancestors(); ?>
	<?php foreach ( $breadcrumbs_paths as $index => $paths ) : ?>
		<ol class="hkb-breadcrumbs" itemscope itemtype="https://schema.org/BreadcrumbList" <?php if ( ! hkb_show_knowledgebase_breadcrumbs() ) : ?>style="display:none;"<?php endif; ?>>
			<?php $last_item_index = count( $paths ) - 1; ?>
			<?php foreach ( $paths as $key => $component ) : ?>
				<li itemprop="itemListElement" itemscope itemtype="https://schema.org/ListItem">
					<?php if ( $key == $last_item_index ) : ?>
					// appending the tenant code with the category url
						<span>
							<span itemprop="name"><?php echo esc_attr( $component['label'] ); ?></span>
							<link itemprop="item" href="<?php the_permalink(); ?>" />
							<input type="hidden" id="category_article" value=<?php echo esc_attr( $component['label'] ); ?>> </input> <?php $vall="123"; ?>
					<?php do_action('setToCache','Hello'); ?>
						</span> 
					<?php else : ?>
						<?php $custom_component_link=esc_url( $component['link'] ); ?>
						<?php $custom_component_link .= "?cmpcod=$cmpcod"; ?>
						<a itemprop="item" href="<?php echo $custom_component_link ?>">
							<span itemprop="name"><?php echo esc_attr( $component['label'] ); ?></span>
							<svg class="hkb-breadcrumbs__icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 19 34.1"><g><path d="M19,17c0,0.5-0.2,1-0.6,1.4L3.4,33.5c-0.8,0.8-2,0.8-2.8,0c-0.8-0.8-0.8-2,0-2.8L14.2,17L0.6,3.4c-0.8-0.8-0.8-2,0-2.8  c0.8-0.8,2-0.8,2.8,0l15.1,15.1C18.8,16,19,16.5,19,17z"/></g></svg>
						</a>
					<?php endif; ?>
					<meta itemprop="position" content="<?php echo esc_attr( $key + 1 ); ?>" />
				</li>               
			<?php endforeach; ?>
		</ol>       
	<?php endforeach; ?>
</div>  
<!-- /.hkb-breadcrumbs -->
