/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.sitemap.service;

import org.broadleafcommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;
import org.broadleafcommerce.common.sitemap.domain.SiteMapURLEntry;
import org.broadleafcommerce.common.sitemap.service.type.SiteMapGeneratorType;
import org.broadleafcommerce.common.sitemap.wrapper.SiteMapURLSetWrapper;
import org.broadleafcommerce.common.sitemap.wrapper.SiteMapURLWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * Responsible for generating site map entries for Category.
 * 
 * @author Joshua Skorton (jskorton)
 */
@Component("blCategorySiteMapGenerator")
public class CategorySiteMapGenerator implements SiteMapGenerator {

    @Resource(name = "blCategoryDao")
    //protected CategoryDao categoryDao;

    /**
     * Returns true if this SiteMapGenerator is able to process the passed in siteMapGeneratorConfiguration.   
     * 
     * @param siteMapGeneratorConfiguration
     * @return
     */
    public boolean canHandleSiteMapConfiguration(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration) {
        return SiteMapGeneratorType.CATEGORY.equals(siteMapGeneratorConfiguration.getSiteMapGeneratorType());
    }

    @Override
    public void addSiteMapEntries(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration, SiteMapBuilder siteMapBuilder) {

        SiteMapURLSetWrapper siteMapURLSetWrapper = new SiteMapURLSetWrapper();
        List<SiteMapURLWrapper> siteMapUrls = siteMapURLSetWrapper.getSiteMapUrlWrappers();
        for (SiteMapURLEntry urlEntry : siteMapGeneratorConfiguration.getCustomURLEntries()) {
            SiteMapURLWrapper siteMapUrl = new SiteMapURLWrapper();

            // location
            siteMapUrl.setLoc(urlEntry.getLocation());

            // changefreq
            if (urlEntry.getSiteMapChangeFreqType() != null) {
                siteMapUrl.setChangeFreqType(urlEntry.getSiteMapChangeFreqType());
            } else {
                siteMapUrl.setChangeFreqType(siteMapGeneratorConfiguration.getSiteMapChangeFreqType());
            }

            // priority
            if (urlEntry.getSiteMapPriorityType() != null) {
                siteMapUrl.setPriorityType(urlEntry.getSiteMapPriorityType());
            } else {
                siteMapUrl.setPriorityType(siteMapGeneratorConfiguration.getSiteMapPriority());
            }

            // lastModDate
            siteMapUrl.setLastModDate(urlEntry.getLastMod());

            siteMapUrls.add(siteMapUrl);
        }

        siteMapBuilder.persistIndexedURLSetWrapper(siteMapURLSetWrapper);
    }

}